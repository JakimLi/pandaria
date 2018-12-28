@verify_json_has_size
@http
Feature: verify json
  verify json has size

  Background:
    * dir: features/verification
    * base uri: http://localhost:10080

  Scenario: has size for object
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

    * verify: '$' has size: 3
    * verify: '$.size()'=3

  Scenario: json object ans string has size
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
    * verify: '$' has size: 3
    * verify: '$.username' has size: 5
    * verify: '$.username' length: 5

    * var: response<-'$'
    * verify: ${response} has size: 3

    * var: username<-'$.username'
    * verify: ${username} has size: 5
    * verify: ${username} length: 5

