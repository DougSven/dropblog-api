# Introduction
Bootstrapped dropwizard application using lazybones.

There is a working service, liquibase migrations, hibernate support and a basic gradle build system including some
shortcuts to the commands for running migrations and starting the service.

Enjoy!

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

# Running The Application

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



