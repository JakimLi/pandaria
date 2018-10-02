@http
Feature: http query parameter
  can work with http query parameters

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  Scenario: parameter in uri
    * uri: /users?name=jakim&age=18
    * send: GET
    * verify: 'name'="jakim"
    * verify: 'age'=18
    * verify: 'iq'=80.0

  Scenario: add extra query parameter
    * var: 'name'='jakim'
    * uri: /users
    * query parameter: 'name'="${name}"
    * query parameter: 'age'='18'
    * send: GET
    * verify: 'name'="jakim"
    * verify: 'age'=18
    * verify: 'iq'=80.0

    * uri: /users?name=jakim
    * query parameter: 'age'='18'
    * send: GET
    * verify: 'name'="jakim"
    * verify: 'age'=18
    * verify: 'iq'=80.0

    * uri: /users
    * query parameter: 'name'='jakim li'
    * send: GET
    * verify: 'name'="jakim"
    * verify: 'age'=18
    * verify: 'iq'=80.0
    * verify: 'iq'=80.0
