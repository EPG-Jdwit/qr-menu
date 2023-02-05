"use strict";
const categoriesUrl = "http://localhost:8081/categories";
const categoryByNameURL = categoriesUrl + "/category?name=";
const dagPrijs = "Dag Prijs";

window.addEventListener("load", () => getCategories());

function scrollToTop() {
  document.body.scrollTop = 0; // For Safari
  document.documentElement.scrollTop = 0; // For Chrome, Firefox, IE and Opera
}

function getCategories() {
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.onload = function() {
    const response = JSON.parse(this.responseText);
    if (response["_embedded"]) {
      document.getElementById("main-content-title").innerHTML = "Categorieën";
      document.getElementById("back-btn").style.visibility = "hidden";

      const categories = response["_embedded"]["categoryList"];
  
      let text = "<table id='category-table'>";
      for (let x in categories) {
        text += "<tr class='category-table-row hover-row' onclick='getCategoryByName(&#34"+categories[x].name +"&#34)'>";
        text += "<td class='category-cell'>" + categories[x].name + "</td>";
        text += "</tr>";
      }
      text += "</table>";
      document.getElementById("content-container").innerHTML = text;
    }
  }
  xmlhttp.open("GET", categoriesUrl);
  xmlhttp.send();
  scrollToTop();
}

function getCategoryByName(name) {
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.onload = function() {
    const response = JSON.parse(this.responseText);
    if (response) {
      const url = response["_links"]["subcategories"].href;
      // getCategoryProducts(url);
      getSubcategories(url, name);
    }
  }
  xmlhttp.open("GET", categoryByNameURL + name);
  xmlhttp.send();
}

function getSubcategories(url, categoryName) {
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.onload = function() {
    const response = JSON.parse(this.responseText);
    if (response["_embedded"]) {
      document.getElementById("main-content-title").innerHTML = categoryName;
      document.getElementById("back-btn").style.visibility = "visible";
      document.getElementById("back-btn").addEventListener("click", getCategories);

      const categories = response["_embedded"]["subcategoryList"];
      let text = "<table id='subcat-table'>";
      for (let x in categories) {
        text += "<tr class='subcat-table-row'>";
        text += "<td class='subcat-cell subcat-name-cell'>";
        text += "<div class='subcat-name'>" + categories[x].name + "</div>";
        text += "<div id='product-container-" + categories[x].id + "'></div>";
        const url = categories[x]["_links"]["products"].href;
        getSubcategoryProducts(url, categories[x].id);
        text += "</td>";
        text += "</tr>";
      }
      text += "</table>";
      document.getElementById("content-container").innerHTML = text;
    }
  }
  xmlhttp.open("GET", url);
  xmlhttp.send();
}

function getSubcategoryProducts(url, id) {
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.onload = function() {
    const response = JSON.parse(this.responseText);
    if (response["_embedded"]) {
      const products = response["_embedded"]["productList"];
      let text = "<table id='product-table'>";
      for (let x in products) {
        text += "<tr class='product-table-row hover-row'>";
        text += "<td class='product-cell product-name-cell'>";
        text += "<div class='product-name'>" + products[x].name + "</div>";
        text += "<div class='product-description'>" + products[x].description + "</div>";
        text += "</td>";
        // Add icons for allergenics
        text += "<td class='product-allergenics-cell'>";
        text += "<div class='product-allergenics-container'>";
        products[x].allergenics.forEach(item =>
          // if (item == 'variabel') {
          //   text += "<p> Variabel </p>"
          // } else {
            text += "<img class='allergenic-img' src='images/allergenics_nl/" + item + ".png' alt='" + item + "'>"
          // }
        );

        text += "</div>";
        text += "</td>";
        if (products[x].price == 0) {
          // If price is zero, it's meant to be shown as "Dag Prijs"
          text += "<td class='product-cell product-price-cell'>" + dagPrijs + "</td>";
        } else {
          text += "<td class='product-cell product-price-cell'>€" + products[x].price.toFixed(2) + "</td>";
        }

        text += "</tr>";
      }
      text += "</table>";
      document.getElementById("product-container-" + id).innerHTML = text;
    }
  }
  xmlhttp.open("GET", url);
  xmlhttp.send();
  scrollToTop();
}