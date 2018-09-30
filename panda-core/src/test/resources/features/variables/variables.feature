@variables
Feature: Variables
  Provides basic variables that can be used for dynamic support

  Background:
    * dir: features/variables
    * base uri: http://localhost:10080

  Scenario: const string
    * var: 'name'='panda'
    * verify: ${name}='panda'

  Scenario: string
    * var: 'name'='panda'
    * var: 'great'="hello ${name}"
    * verify: ${great}='hello panda'
    * verify: ${great}="hello ${name}"

  Scenario: integer
    * var: 'age'=18
    * verify: ${age}=18

  Scenario: double
    * var: 'age'=18.0
    * verify: ${age}=18.0

  Scenario: from json
    * uri: /not_important
    * send: GET
    * status: 200
    * var: 'name'<-'$.name'
    * var: 'age'<-'$.age'
    * var: 'iq'<-'$.iq'
    * verify: ${name}='panda'
    * verify: ${age}=18
    * verify: ${iq}=80.0

  Scenario: variable being replaced in uri
    * var: 'path'="not_important"
    * uri: /${path}
    * send: GET
    * status: 200
    * verify: '$.name'='panda'
    * verify: '$.age'=18
    * verify: '$.iq'=80.0

  Scenario: variable used in request file
    * var: 'name'='someone'
    * uri: /users
    * request body: requests/someone.json
    * send: POST
    * status: 201

