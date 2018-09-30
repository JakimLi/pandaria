@variables
Feature: Variables
  Provides basic variables that can be used for dynamic support

  Background:
    * dir: features/variables
    * base uri: http://localhost:10080

  Scenario: const string
    * var 'name'='panda'
    * verify: ${name}='panda'

  Scenario: string
    * var 'name'='panda'
    * var 'great'="hello ${name}"
    * verify: ${great}='hello panda'
    * verify: ${great}="hello ${name}"

  Scenario: integer
    * var 'age'=18
    * verify: ${age}=18

  Scenario: double
    * var 'age'=18.0
    * verify: ${age}=18.0
