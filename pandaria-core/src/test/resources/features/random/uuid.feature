Feature: generate random number for test
  generate random number for testing

  Background:
    * dir: features/random

  Scenario: generate random number
    * var: 'uuid'=random uuid
    * verify: ${uuid} length: 36
