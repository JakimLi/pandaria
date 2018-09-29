@http
Feature: Http feature
  Basic http operations with verifications

  Background:
    * dir: features/http

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

  Scenario: simple post without request body
    * uri: http://localhost:10080/empty_request
    * send: POST
    * status: 201
    * verify: '$.name'='someone'

  Scenario: simple post with json from file
    * uri: http://localhost:10080/users
    * request body: requests/user.json
    * send: POST
    * status: 200
    * verify: '$.id'='auto-generated'
    * verify: '$.username'='jakim'
    * verify: '$.age'=18

  Scenario: simple post with from file in absolute path
    * uri: http://localhost:10080/users
    * request body: classpath:user.json
    * send: POST
    * status: 200
    * verify: '$.id'='auto-generated'
    * verify: '$.username'='jakim'
    * verify: '$.age'=18
