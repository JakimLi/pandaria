@http
Feature: variables used with complex situation
  can explore the structure of the complex variable

  Background:
    * dir: features/variables
    * base uri: http://localhost:10080

  Scenario: variable compare with variable
    * uri: /users
    * request body:
    """
    {"username": "jakim", "age": 18}
    """
    * send: POST
    * status: 200
    * verify: '$.id'='auto-generated'
    * verify: '$.username'='jakim'
    * verify: '$.age'=18
    
    * var: user<-'$'

    * verify: '$.id'=${user.id}
    * verify: '$.username'=${user.username}
    * verify: '$.age'=${user.age}

