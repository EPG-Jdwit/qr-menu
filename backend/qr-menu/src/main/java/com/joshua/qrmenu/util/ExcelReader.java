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

/**
 * Class to read and parse data from a Excel file and populate the database.
 */
public class ExcelReader {

    private ProductService productService;
    private CategoryService categoryService;
    private SubcategoryService subcategoryService;

    /**
     * Constructor
     * @param productService: A ProductService.
     * @param categoryService: A CategoryService.
     * @param subcategoryService: A SubcategoryService.
     */
    public ExcelReader(ProductService productService,
                       CategoryService categoryService,
                       SubcategoryService subcategoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.subcategoryService = subcategoryService;
    }

    /**
     * Read an Excel file in and parse the data to populate the database.
     * @param fileLocation : The relative path to the file location.
     * @throws IOException
     * @throws NotFoundException : When an Entity isn't found.
     * @throws InputException : When a required field of a
     * @throws AlreadyExistsException
     */
    public void readExcel(String fileLocation) throws IOException, NotFoundException, InputException, AlreadyExistsException {
        // Open the file
        FileInputStream file = new FileInputStream(fileLocation);

        Workbook workbook = null;
        // create a workbook dependent on the file extension
        if (fileLocation.endsWith(".xls") || fileLocation.endsWith(".xlsx")) {
            // Create a workbook based on the extension
            if (fileLocation.endsWith(".xls")) {
                workbook = new HSSFWorkbook(file);
            } else {
                workbook = new XSSFWorkbook(file);
            }

            // Iterate over the different sheets. Each sheet is a category.
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Long categoryId = categoryService.createNewCategory(new NewCategory(sheet.getSheetName(), i)).getCategoryId();
                String subcategoryName = "";
                // Order is used as a field to display items in a set order
                int subcategoryOrderNr = -1;
                List<Long> productIds = new ArrayList<>();
                // Iterate over each row with collumn format: Subcategory|name|price|description|allergenics
                for (Row row : sheet) {
                    // Ignore header row
                    if (row.getRowNum() != 0) {
                        // Get the subcategory name
                        String subcategoryColumn = row.getCell(0).getRichStringCellValue().getString();
                        // Different subcategory reached
                        if (!subcategoryColumn.equals(subcategoryName)) {
                            // Initial pass
                            if (subcategoryOrderNr == -1) {
                                subcategoryName = subcategoryColumn;
                                subcategoryOrderNr += 1;
                            } else {
                                // Different subcategory has been reached, create a subcategory with the previously parsed data
                                subcategoryService.createNewSubcategory(
                                        categoryId,
                                        new NewSubcategory(subcategoryName, productIds, subcategoryOrderNr)
                                );
                                productIds = new ArrayList<>();
                                subcategoryName = subcategoryColumn;
                                subcategoryOrderNr += 1;
                            }
                        }

                        // Parse the product
                        String productName = row.getCell(1).getRichStringCellValue().getString();
                        Product product;
                        try {
                            // Existing product
                            product = productService.getProductByName(productName);
                        } catch (NotFoundException e) {
                            // Non-existing product
                            double price;
                            // Boundary case: when a cell contains "D.Pr" it means daily price. The client should ask the
                            // waiter what the current price is. Set the price to zero for the double value in the database
                            if (row.getCell(2).getCellType() != CellType.NUMERIC && row.getCell(2).getRichStringCellValue().getString().strip().equals("D.Pr")) {
                                // TODO: find workaround for Dag Prijs
                                price = 0.0;
                            } else {
                                // Parse the price
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
                                // Split the comma separated list of allergenics
                                List<String> results = Arrays.stream(allergenics.split(",")).toList();
                                for (String result : results) {
                                    // Remove leading and trailing spaces
                                    String stripped = result.strip();
                                    // If the allergenic is a valid one, add it to the list
                                    if (ProductEntity.VALID_ALLERGENICS.contains(stripped)) {
                                        allergenicsList.add(stripped);
                                    }
                                }
                            }
                            // TODO: ORDERNR OF PRODUCT
                            // Create a new product
                            NewProduct newProduct = new NewProduct(productName, price, description, allergenicsList);
                            product = productService.createNewProduct(newProduct);
                        }
                        productIds.add(product.getProductId());
                    }
                }
                // Add the last subcategory. This hasn't happened as a subcategory only gets created in the for-loop
                // when a change of subcategory name is perceived.
                if (subcategoryOrderNr >= 0 ) {
                    subcategoryService.createNewSubcategory(
                            categoryId,
                            new NewSubcategory(subcategoryName, productIds, subcategoryOrderNr)
                    );
                }
            }
            // Close the workbook
            workbook.close();
        }
    }
}
