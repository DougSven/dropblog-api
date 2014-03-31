# Dropblog
Bootstrapped dropwizard application using lazybones.

## Setup
Requires gradle and foreman.

Create .env file to define admin user for update access
    ```
    ADMIN_USER=user
    ADMIN_PASSWORD=pwd
    ```
## Running the Application

To test the example application run the following commands.

* To run the tests run

        gradle test

* To package the example run.

        gradle shadowJar

* To drop an existing h2 database run.

        gradle dropAll

* To setup the h2 database run.

        gradle migrate

* To run the server run.

        gradle run

* To run gradle with foreman environment server run.

        foreman run gradle run

## Additional Considerations
* Add SSL configuration
* Persist Users to database (hash and salt password)
* Create a Blog element which contains a collection of Posts and associated Users
* Add a variety of search functionality for Posts i.e. get by create date.
* Hook in postgres database
* Add database health checks
* Determine best way to do end to end testing
* Add DAO database access testing
* Upgrade to Dropwizard 0.7.0


## Directory Layout

    <proj>
        +- src
            +- main
            |   +- groovy
            |   |     +- your.package.structure
            |   |           +- core
            |   |           +- db
            |   |           +- healthchecks
            |   |           +- resources
            |   |           +- core
            |   +- resources
            |
            +- test
                +- groovy
                |     +- // Spock tests in here!
                +- resources
                      +- fixtures