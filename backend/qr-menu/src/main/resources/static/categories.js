"use strict";
const categoriesUrl = "http://localhost:8081/categories";

window.addEventListener("load", () => getCategories());

function getCategories() {
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.onload = function() {
    const response = JSON.parse(this.responseText);
    if (response["_embedded"]) {
      const categories = response["_embedded"]["categoryList"];
  
      let text = "<table id='category-table'>";
      for (let x in categories) {
        text += "<tr class='category-table-row'>";
        text += "<td class='category-cell'>" + categories[x].name + "</td>";
        text += "</tr>";
      }
      text += "</table>";
      document.getElementById("categories-container").innerHTML = text;
    }
  }
  xmlhttp.open("GET", categoriesUrl);
  xmlhttp.send();
}