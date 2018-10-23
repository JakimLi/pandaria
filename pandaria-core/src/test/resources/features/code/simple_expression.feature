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
