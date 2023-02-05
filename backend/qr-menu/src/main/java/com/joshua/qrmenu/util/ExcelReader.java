package com.joshua.qrmenu.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;


import com.joshua.qrmenu.endpoints.exceptions.AlreadyExistsException;
import com.joshua.qrmenu.endpoints.exceptions.InputException;
import com.joshua.qrmenu.endpoints.exceptions.NotFoundException;
import com.joshua.qrmenu.models.entities.ProductEntity;
import com.joshua.qrmenu.models.json.NewCategory;
import com.joshua.qrmenu.models.json.NewProduct;
import com.joshua.qrmenu.models.json.NewSubcategory;
import com.joshua.qrmenu.models.json.Product;
import com.joshua.qrmenu.services.CategoryService;
import com.joshua.qrmenu.services.ProductService;
import com.joshua.qrmenu.services.SubcategoryService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    private ProductService productService;
    private CategoryService categoryService;
    private SubcategoryService subcategoryService;

    public ExcelReader(ProductService productService,
                       CategoryService categoryService,
                       SubcategoryService subcategoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }
    public void readExcel(String fileLocation) throws IOException, NotFoundException, InputException, AlreadyExistsException {
        FileInputStream file = new FileInputStream(fileLocation);

        Workbook workbook = null;
        if (fileLocation.endsWith(".xls") || fileLocation.endsWith(".xlsx")) {
            // Create a workbook based on the extension
            if (fileLocation.endsWith(".xls")) {
                workbook = new HSSFWorkbook(file);
            } else {
                workbook = new XSSFWorkbook(file);
            }


            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Long categoryId = categoryService.createNewCategory(new NewCategory(sheet.getSheetName(), i)).getCategoryId();
                String subcategoryName = "";
                int subcategoryOrderNr = -1;
                List<Long> productIds = new ArrayList<>();
                for (Row row : sheet) {
                    // Ignore header
                    if (row.getRowNum() != 0) {
                        String subcategoryColumn = row.getCell(0).getRichStringCellValue().getString();
                        if (!subcategoryColumn.equals(subcategoryName)) {
                            if (subcategoryOrderNr == -1) {
                                subcategoryName = subcategoryColumn;
                                subcategoryOrderNr += 1;
                            } else {
                                subcategoryService.createNewSubcategory(
                                        categoryId,
                                        new NewSubcategory(subcategoryName, productIds, subcategoryOrderNr)
                                );
                                productIds = new ArrayList<>();
                                subcategoryName = subcategoryColumn;
                                subcategoryOrderNr += 1;
                            }
                        }

                        // Add the product
                        String productName = row.getCell(1).getRichStringCellValue().getString();
                        Product product;
                        try {
                            product = productService.getProductByName(productName);
                        } catch (NotFoundException e) {
                            double price;
                            if (row.getCell(2).getCellType() != CellType.NUMERIC && row.getCell(2).getRichStringCellValue().getString().strip().equals("D.Pr")) {
                                // TODO: find workaround for Dag Prijs
                                price = 0.0;
                            } else {
                                price = row.getCell(2).getNumericCellValue();
                            }
                            // Add description if provided
                            String description = "";
                            if (row.getCell(3) != null) {
                                description = row.getCell(3).getRichStringCellValue().toString();
                            }
                            // Add allergenics if provided
                            List<String> allergenicsList = new ArrayList<>();
                            if (row.getCell(4) != null) {
                                String allergenics = row.getCell(4).getRichStringCellValue().toString();
                                List<String> results = Arrays.stream(allergenics.split(",")).toList();
                                for (String result : results) {
                                    // Remove leading and trailing spaces
                                    String stripped = result.strip();
                                    if (ProductEntity.VALID_ALLERGENICS.contains(stripped)) {
                                        allergenicsList.add(stripped);
                                    }
                                }
                            }
                            // TODO: ORDERNR
                            NewProduct newProduct = new NewProduct(productName, price, description, allergenicsList);
                            product = productService.createNewProduct(newProduct);
                        }
                        productIds.add(product.getProductId());
                    }
                }
                // Add the last subcategory
                if (subcategoryOrderNr >= 0 ) {
                    subcategoryService.createNewSubcategory(
                            categoryId,
                            new NewSubcategory(subcategoryName, productIds, subcategoryOrderNr)
                    );
                }
            }
            workbook.close();
        }
    }
}
