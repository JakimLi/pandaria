Feature: simple expression
  Use code to write simple calculation

  Scenario: simple expression

    * var: 'three'=3

    * var: 'five'=code:
    """
    ${three} + 3
    """

    * verify: ${five}=6

    * var: 'zero'=code: ${three} - 3
    * verify: ${zero}=0