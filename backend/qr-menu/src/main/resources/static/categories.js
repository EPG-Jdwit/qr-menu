"use strict";
const categoriesUrl = "http://localhost:8081/categories";
const categoryByNameURL = categoriesUrl + "/category?name=";

window.addEventListener("load", () => getCategories());

function getCategories() {
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.onload = function() {
    const response = JSON.parse(this.responseText);
    if (response["_embedded"]) {
      const categories = response["_embedded"]["categoryList"];
  
      let text = "<table id='category-table'>";
      for (let x in categories) {
        text += "<tr class='category-table-row' onclick='getCategoryByName(&#34"+categories[x].name +"&#34)'>";
        text += "<td class='category-cell'>" + categories[x].name + "</td>";
        text += "</tr>";
      }
      text += "</table>";
      document.getElementById("item-list-container").innerHTML = text;
    }
  }
  xmlhttp.open("GET", categoriesUrl);
  xmlhttp.send();
}

function getCategoryByName(name) {
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.onload = function() {
    const response = JSON.parse(this.responseText);
    if (response) {
      const url = response["_links"]["categoryProducts"].href;
      getCategoryProducts(url);
    }
  }
  xmlhttp.open("GET", categoryByNameURL + name);
  xmlhttp.send();
}

function getCategoryProducts(url) {
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.onload = function() {
    const response = JSON.parse(this.responseText);
    if (response["_embedded"]) {
      const categories = response["_embedded"]["productList"];
      let text = "<table id='product-table'>";
      for (let x in categories) {
        text += "<tr class='product-table-row'>";
        text += "<td class='product-name-cell'>" + categories[x].name + "</td>";
        text += "<td class='product-price-cell'>€" + categories[x].price + "</td>";
        text += "</tr>";
      }
      text += "</table>";
      document.getElementById("item-list-container").innerHTML = text;
    }
  }
  xmlhttp.open("GET", url);
  xmlhttp.send();
}