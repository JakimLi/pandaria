@code
Feature: simple expression
  Use code to write simple calculation

  Background:
    * dir: features/code

  Scenario: simple expression

    * var: 'three'=3

    * var: 'six'=code:
    """
    ${three} + 3
    """

    * verify: ${six}=6

    * var: 'zero'=code: ${three} - 3
    * verify: ${zero}=0

    * var: 'ten'=code file: six_add_four.js
    * verify: ${ten}=10

  Scenario: verify with code

    * var: 'age'=16
    * var: 'iq'=90.0

    * uri: http://localhost:10080/not_important
    * send: get
    * verify: '$.age'=code: ${age} + 2
    * verify: '$.iq'=code: ${iq} - 10

    * verify: '$.age'!=code: ${age} + 3
    * verify: '$.iq'!=code: ${iq} - 11

    * verify: '$.age'=code:
    """
    ${age} + 2
    """
    * verify: '$.iq'=code:
    """
    ${iq} - 10
    """

    * verify: '$.age'=code file: 18.js
    * verify: '$.iq'!=code file: 18.js

  Scenario: verify variable against code
    * var: 'age'=16
    * var: 'iq'=129.0

    * var: 'eighty'=80
    * var: 'name'=code: ${iq} / 3

    * verify: ${eighty}=code: ${age} * 5
    * verify: ${name}=code: ${iq} / 3
    * verify: ${name}=double: 43.0

    * verify code: ${name} == ${iq} / 3
    * verify code:
    """
    ${name} != ${iq} % 3
    """
    * verify code file: verification.js
