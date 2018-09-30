@http
Feature: Http feature
  Basic http operations with verifications

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  Scenario: simple get
    * uri: /users/me
    * send: GET
    * status: 200
    * verify: '$.username'='jakim'
    * verify: '$.age'=18

  Scenario: simple post with jsn
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

  Scenario: simple post without request body
    * uri: /empty_request
    * send: POST
    * status: 201
    * verify: '$.name'='someone'

  Scenario: simple post with json from file
    * uri: /users
    * request body: requests/user.json
    * send: POST
    * status: 200
    * verify: '$.id'='auto-generated'
    * verify: '$.username'='jakim'
    * verify: '$.age'=18

  Scenario: simple post with from file in absolute path
    * uri: /users
    * request body: classpath:user.json
    * send: POST
    * status: 200
    * verify: '$.id'='auto-generated'
    * verify: '$.username'='jakim'
    * verify: '$.age'=18

  Scenario: get with http header
    * uri: /custom_header
    * header: 'Accept'='text.plain'
    * send: GET
    * status: 200
    * response body:
    """
    success
    """

  Scenario: verify plain text response to content in file
    * uri: /custom_header
    * header: 'Accept'='text.plain'
    * send: GET
    * status: 200
    * response body: responses/success.txt

  Scenario: simple put
    * uri: /users/me
    * request body:
    """
    {"username": "lj"}
    """
    * send: PUT
    * status: 200
    * verify: '$.username'='lj'

  Scenario: simple delete
    * uri: /users/20
    * send: DELETE
    * status: 200

  Scenario: simple head
    * uri: /users
    * send: HEAD
    * status: 200
    * response header: 'test'='first,second,third'
    * response header: 'Date'='Thur, 2018 12'

  Scenario: simple patch
    * uri: /users/20
    * request body:
    """
    {"username": "lj"}
    """
    * send: PATCH
    * status: 200
    * verify: '$.username'='lj'

  Scenario: simple options
    * uri: /users
    * send: OPTIONS
    * status: 200
    * response header: 'Allow'='OPTIONS, GET, HEAD'
