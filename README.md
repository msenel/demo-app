Maven project.

Please use maven commands to import the project. 

Software-Stack:

- Spring Framework
- Hibernate
- HSQLDB - Java In-Memory Database
- Apache Tiles


Notes:
- I just set up Tiles without content to demonstrate a possible way for adding jsp's
- Too much overhead for a task like this. Spring Boot usage would be better here.
  My intention was to demonstrate the setup of a classical Spring Web App configuration
- Instead of HSQLDB, I could use other supported RMDBS (like Postgres and Mysql). In this case it is more easy to use.


Testing:
- Functional Tests should be written with Junit for each Layer/Class/Method
- To run automated acceptance tests written in a behavior-driven development (BDD) style, Cucumber in combination with
  DBUnit and Mockito would be a good choice.

  For example:

  Scenario: User registration Flow
    Given Relational data as db/users/users1.xml
    When I request user registration service with parameters Mustafa, Senel, xxxxx
    Then Service should return HTTP Status 201

  Scenario: User registration Flow
    Given Relational data as db/users/users1.xml
    When I request user registration service with parameters Mustafa, Senel
    Then Service should return HTTP Status 400





