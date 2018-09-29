@http
Feature: Http feature
  This contains scenarios that different kind of http methods

  Scenario: simple get
    * uri: http://localhost:10080/users/me
    * send: GET
    * status: 200
    * verify: '$.username'='jakim'
    * verify: '$.age'=18
