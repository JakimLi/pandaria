@verify_string
@http
Feature: verify string
  verify strings

  Background:
    * dir: features/verification
    * base uri: http://localhost:10080

  Scenario: equals
    * uri: /users/me
    * send: GET
    * status: 200
    * verify: '$.username'='jakim'

    * var: 'kim'="kim"
    * var: 'user'='jakim'
    * verify: ${user}="ja${kim}"

    * verify: '$.username'="jakim"
    * verify: '$.username'="ja${kim}"

    * verify: ${user}!='notjakim'
    * verify: ${user}!="notja${kim}"

    * verify: '$.username'!="notjakim"
    * verify: '$.username'!="notja${kim}"


  Scenario: contains
    * uri: /users/me
    * send: GET
    * status: 200
    * verify: '$.username'='jakim'
    * verify: '$.username' contains: 'kim'

    * var: 'username'="jakim"
    * verify: ${username} contains: 'kim'

    * var: 'contained'='kim'
    * verify: '$.username' contains: "${contained}"
    * verify: ${username} contains: "${contained}"
