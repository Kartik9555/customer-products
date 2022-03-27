# customer-products
Customer products Service API

The back-end service requires Java 8. The code is in the ``src/main/java`` directory.

### How to build and execute the back-end?

```
Update the URL and DB name of the Mongo instance in the below properties in the application.properties file in the path src/main/resources
spring.data.mongodb.uri
spring.data.mongodb.database

- Run the following on the command line: (Windows)
```bash
cd PATH/TO/PROJECT
mvnw.cmd clean spring-boot:run
```
- __Or__ from Intellij IDEA (during the development process): Run spring application (manually selecting in the run menu or just clicking on the play button next to the main method inside the ``com.ecommerce.shopping.customerproducts.CustomerProductsApplication`` class)

## Technologies that have been used

- [Spring Framework](https://spring.io/projects/spring-framework): Java based enterprise application framework
- [Spring Boot](https://spring.io/projects/spring-boot): Spring based web application framework
- [Maven](https://maven.apache.org/): Software project management and comprehension tool

## APIs exposed by the service:

###Customers
- __GET__    /customers: Returns paginated list of all customers
- __GET__    /customer/{customerId}: Returns customer by id
- __POST__   /customers: Create a new customer
- __DELETE__ /customers/{customerId}: Delete customer
- __PUT__    /customers/{customerId}: Edit customer

###Products
- __GET__    /customers/{customerId}/products: Returns paginated list of all customer products
- __GET__    /products/{productId}: Returns product by id
- __POST__   /customers/{customerId}/products: Create a new product for customer
- __DELETE__ /products/{productId}: Delete product
- __PUT__    /products/{productId}: Edit product