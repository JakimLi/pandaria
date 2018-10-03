@http
Feature: verify numbers
  verify numbers

  Scenario: integer equals
    * uri: http://localhost:10080/users/me
    * send: GET
    * status: 200
    * verify: '$.username' not null
