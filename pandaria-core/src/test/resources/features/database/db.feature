@variables
Feature: database
  database related operations

  Background:
    * dir: features/variables

  Scenario: query
    * execute sql:
    """
    DROP TABLE IF EXISTS USERS;

    CREATE TABLE USERS(
        NAME VARCHAR(256) NOT NULL,
        AGE INTEGER(5) NOT NULL
    );

    INSERT INTO USERS(NAME, AGE) VALUES('jakim', 18);
    """

    * query:
    """
    SELECT NAME, AGE FROM USERS
    """
    * verify: '$[0].name'='jakim'
    * verify: '$[0].age'=18
