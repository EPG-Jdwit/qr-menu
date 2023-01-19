"use strict";

// const btn = document.getElementById("btn");
// if (btn) {
//     btn.addEventListener("click", getProducts);
// }


function populate() {
  let pageUrl = "http://localhost:8081/populate";
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.open("GET", pageUrl);
  xmlhttp.send();
}

function getProducts() {
  let pageUrl = "http://localhost:8081/products";
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.onload = function() {
    const response = JSON.parse(this.responseText);
    const productList = response["_embedded"]["productList"]
    if (productList) {
      let text = "<table border='1'>";
      text += "<tr><th>Name</th><th>Price</th><th>Description></th></tr>";
      for (let x in productList) {
        text += "<tr>";
        text += "<td>" + productList[x].name + "</td>";
        text += "<td>" + productList[x].price + "</td>";
        text += "<td>" + productList[x].description + "</td>";
        text += "</tr>";
      }
      text += "</table>";
      document.getElementById("demo").innerHTML = text;
    }
  }
  xmlhttp.open("GET", pageUrl);
  xmlhttp.send();
}

function addNewProduct() {
  let pageUrl = "http://localhost:8081/products";
  const name = document.getElementById("nname").value;
  const price = document.getElementById("nprice").value;
  const description = document.getElementById("ndescription").value;

  const newProduct = {name: name, price: price, description: description}
  const myJson = JSON.stringify(newProduct);
  console.log(myJson);
  const xmlhttp = new XMLHttpRequest();
  xmlhttp.onload = function() {
    getProducts();
  }
  xmlhttp.open("POST", pageUrl);
  xmlhttp.setRequestHeader("Content-type", "application/json");
  xmlhttp.send(myJson);
}
