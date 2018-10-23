@variables
Feature: Variables
  Provides basic variables that can be used for dynamic support

  Background:
    * dir: features/variables
    * base uri: http://localhost:10080

  Scenario: initial value from configuration file
    * verify: ${environment}="test"
    * var: 'environment'="production"
    * verify: ${environment}="production"

  Scenario: const string
    * var: 'name'='panda'
    * verify: ${name}='panda'

  Scenario: string
    * var: 'name'='panda'
    * var: 'great'="hello ${name}"
    * var: 'another'='hello panda'
    * verify: ${great}='hello panda'
    * verify: ${great}="hello ${name}"
    * verify: ${great}=${another}

  Scenario: integer
    * var: 'age'=18
    * verify: ${age}=18

  Scenario: double
    * var: 'age'=18.0
    * verify: ${age}=double: 18.0

  Scenario: from json
    * uri: /not_important
    * send: GET
    * status: 200
    * var: 'name'<-'$.name'
    * var: 'age'<-'$.age'
    * var: 'iq'<-'$.iq'
    * verify: ${name}='panda'
    * verify: ${age}=18
    * verify: ${iq}=double: 80.0

  Scenario: variable being replaced in uri
    * var: 'path'="not_important"
    * uri: /${path}
    * send: GET
    * status: 200
    * verify: '$.name'='panda'
    * verify: '$.age'=18
    * verify: '$.iq'=double: 80.0

  Scenario: variable used in request file
    * var: 'name'='someone'
    * uri: /users
    * request body: requests/someone.json
    * send: POST
    * status: 201

  Scenario: variable used in request content
    * var: 'name'='someone'
    * uri: /users
    * request body:
    """
    {
      "name": "${name}"
    }
    """
    * send: POST
    * status: 201

  Scenario: variable used in request file and response body
    * var: 'name'='someone'
    * uri: /users
    * request body: requests/someone.json
    * send: POST
    * status: 201
    * var: 'username'='someone_created'
    * response body: responses/response.json
    * response body:
    """
    {"user":"${username}"}
    """
