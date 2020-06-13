@debug
@http
Feature: debug
  common debugging tasks

  Background:
    * base uri: http://localhost:10080
    * dir: features/debug

  Scenario: print http response
    * uri: /users
    * request body: classpath:user.json
    * send: POST
    * status: 200
    * verify: '$.id'='auto-generated'
    * verify: '$.username'='jakim'
    * verify: '$.age'=18
    * var: var_id<-'$.id'

    * print: "begin"
    * print: "@{$}"
    * print: "@{$.username}"
    * print: "<string>"
    * print: "${var_id}"
    * print: '${var_id}'
    * print: "end"
