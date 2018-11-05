@outline
Feature: data driven
  data driven should work with scenario outline

  Background:
    * dir: features/outline
    * base uri: http://localhost:10080

  Scenario Outline:
    * uri: /users
    * request body:
    """
    { "username": "<username>" }
    """
    * send: POST
    * status: 200
    * verify: '$.username'='<username>'

    Examples:
      | username |
      | jakim    |
      | alice    |
      | bob      |
      | steve    |


  Scenario Outline:
    * var: 'username'='<username>'

    * uri: /users
    * request body: requests/user.json
    * send: POST
    * status: 200
    * verify: '$.username'='<username>'

    Examples:
      | username |
      | jakim    |
      | alice    |
      | bob      |
      | steve    |