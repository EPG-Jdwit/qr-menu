# qr-menu
A prototype of a QR menu app designed for De Barkentijn Oostduinkerke. <br/>
Data can be parsed from an excel file located in `backend\qr-menu\src\main\java\com\joshua\qrmenu\util` by sending
a GET request to the `/populate` endpoint. <br/>
Other endpoints for CRUD operations are available as described by the [API documentation](https://jaldwit.github.io/qr-menu/).

## Menu page with a list of clickable categories.
![](info/category-list.png)
## Category view with subcategories each containing products.
Each product has a name, description, price and a list of allergenics.
![](info/kleine-honger.png)
## Suggested items
![](info/suggesties.png)
## Contact page with google map location displayed
![](info/contact.png)