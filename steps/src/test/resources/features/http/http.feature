@http
Feature: Http feature
  Basic http operations with verifications

  Scenario: simple get
    * uri: http://localhost:10080/users/me
    * send: GET
    * status: 200
    * verify: '$.username'='jakim'
    * verify: '$.age'=18

  Scenario: simple post with jsn
    * uri: http://localhost:10080/users
    * request body:
    """
    {"username": "jakim", "age": 18}
    """
    * send: POST
    * status: 200
    * verify: '$.id'='auto-generated'
    * verify: '$.username'='jakim'
    * verify: '$.age'=18
