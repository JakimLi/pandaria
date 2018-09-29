@http
Feature: Http feature
  This contains scenarios that different kind of http methods

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
