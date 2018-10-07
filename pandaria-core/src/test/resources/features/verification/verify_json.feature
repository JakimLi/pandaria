@verify_string
@http
Feature: verify string
  verify strings

  Background:
    * dir: features/verification
    * base uri: http://localhost:10080

  Scenario: same json: simple json
    * uri: /users/me
    * send: get
    * verify: '$' same json:
    """
    {
      "iq": 80.0,
      "username": "jakim",
      "age": 18
    }
    """
    * verify: '$' same json: responses/jakim.json
