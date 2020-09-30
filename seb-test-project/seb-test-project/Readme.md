# Currency exchange application

Software created to check and calculate exchange rates of 201 currencies

## Technology

The project was developed with IntelliJ IDEA
Project SDK: Java JDK 1.8

### External libraries/modules
* H2 Database
* Maven
* Spring

## Instructions

The software consists of multiple plugins and libraries which are imported to the project via maven in pom.xml file. To run the program user has to run SebTestProject.java class located in lt.lb package. The program has 3 main functions:

### Check all currencies
In order to check all possible currencies and their exchange rates from Euro, while the program is running, user has to enter the url address in their browser (localhost:8080/displayAll).

### Exchange
To check the value in another currency, the user has to enter an url in a following format:
localhost:8080/exchange?from={user input}&to={user input}&amount={user input}

#### Fields
* **from** - the ccy code of the currency which is being exchanged
* **to** - the ccy code of the currency to what the value has to be exchanged
* **amount** - the value which is being exchanged
Example url address:
localhost:8080/exchange?from=AUD&to=CAD&amount=20
The program will output the value of 20 Australian dollars in Canadian dollars and at what rate the exchange was made.

### Check the increase or decrease of a single currency
To check how the value of a currency has change over time, the user has to enter an url in a following format:
localhost:8080/exchangeOverPeriod?ccy={user input}&from={user input}&to={user input}

#### Fields
* **ccy** - the ccy code of the currency that is being checked
* **from** - from what date the currency needs to be checked
* **to** - until what date the currency needs to be checked
Example url address:
localhost:8080/exchangeOverPeriod?ccy=AUD&from=2015-01-01&to=2015-02-01
The program will check and output what was Australian dollars's exchange rate to Euro in 2015-01-01 and whether it has increased or decreased by 2015-02-01
