@wait_until
Feature: wait until
  wait and retry until the verification pass or failing on max retry time exceeds

  Background:
    * dir: features/wait
    * base uri: http://localhost:10080

  Scenario: wait until http response expected
    * wait: 1000ms times: 3
    * uri: /sequence
    * send: GET
    * response body:
    """
    3
    """

    * uri: /sequence
    * send: GET
    * response body:
    """
    4
    """

    * wait: 1s times: 3
    * uri: /sequence
    * send: GET
    * response body:
    """
    5
    """

    * uri: /sequence
    * send: GET
    * response body:
    """
    6
    """

  Scenario: wait until database query results expected
    * execute sql:
    """
    DROP TABLE IF EXISTS USERS;

    CREATE TABLE USERS(
        NAME VARCHAR(256) NOT NULL,
        AGE INTEGER(5) NOT NULL
    );

    INSERT INTO USERS(NAME, AGE) VALUES('jakim', 18);
    """

    * wait: 1000ms times: 3
    * query:
    """
    SELECT NAME, AGE FROM USERS;
    """
    * verify: '$[0].name'="jakim"
    * verify: '$[0].age'=18
