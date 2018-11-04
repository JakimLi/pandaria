@http
Feature: request from last response
  Directly use last http response as request

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  Scenario: request directly from last response

    * uri: /users/me
    * send: get
    * verify: '$.username'='jakim'
    * verify: '$.age'=18
    * verify: '$.iq'=double: 80.0

    * uri: /users
    * request body:
    """
    { "username": @{$.username}}
    """
    * send: POST
    * status: 200
    * verify: '$.id'='auto-generated'
    * verify: '$.username'='jakim'
    * verify: '$.age'=18

    * uri: /users
    * request body:
    """
    @{$}
    """
    * send: POST
    * status: 200
    * verify: '$.id'='auto-generated'
    * verify: '$.username'='jakim'
    * verify: '$.age'=18

