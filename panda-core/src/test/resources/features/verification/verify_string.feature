@verify_string
@http
Feature: verify string
  verify strings

  Background:
    * dir: features/verification
    * base uri: http://localhost:10080

  Scenario: contains
    * uri: /users/me
    * send: GET
    * status: 200
    * verify: '$.username'='jakim'
    * verify: '$.username' contains: 'kim'
