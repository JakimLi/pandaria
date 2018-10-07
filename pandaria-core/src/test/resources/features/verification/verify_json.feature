@verify_string
@http
Feature: verify json
  verify json

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


  Scenario: same json: with array different order
    * uri: /users/list
    * send: get
    * verify: '$' same json:
    """
    [
      {
        "name": "jakim",
        "friends": [
          "james", "jack"
        ]
      },
      {
        "name": "smart",
        "friends": ["sue", "lucy"]
      },
      {
        "name": "haha"
      }
    ]
    """

    * verify: '$[0].friends' same json:
    """
    ["jack", "james"]
    """

    * var: 'response'<-'$'
    * verify: ${response} same json:
    """
    [
      {
        "name": "jakim",
        "friends": [
          "james", "jack"
        ]
      },
      {
        "name": "smart",
        "friends": ["sue", "lucy"]
      },
      {
        "name": "haha"
      }
    ]
    """
