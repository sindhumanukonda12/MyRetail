# MyRetail
RESTful service that can retrieve product and price details by ID . It is build with

Java8
spring Boot
Rest
Mongo
Embedded mongo
Swagger
Jacoco

How to run it:

1.clone the project to you local
2.Use any of the IDE intellij/Eclipse 
3.Run as a springboot application.
4.It the startup of the application embedded db is created in application and inserts data into it.
eg : (13860428, 15.45, "USD") and (15117729, 13.44, "USD")
5.rest call to retrive details or update prize using postman or swagger.

Port : 8081

To retrive the product and prize details by ID:

Rest URL: http://localhost:8081/myRetail/v1/products/{id} Request Method: GET

eg: Request: http://localhost:8081/myRetail/v1/products/13860428
Response:   {
    "id": 13860428,
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": 15.45,
        "currency_code": "USD"
    }
}

To update the product prize in data store:

Rest URL: http://localhost:8081/myRetail/v1/products/{id} Request Method: PUT
      requestBody: {
  "current_price": {
    "currency_code": "string",
    "value": 0
  },
  "id": 0,
  "name": "string"
}

Swagger URL:
http://localhost:8081/


