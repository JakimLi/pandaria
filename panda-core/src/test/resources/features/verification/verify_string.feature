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

  Scenario: starts with
    * uri: /users/me
    * send: GET
    * status: 200
    * verify: '$.username'='jakim'
    * verify: '$.username' starts with: 'jak'

    * var: 'username'="jakim"
    * verify: ${username} starts with: 'jak'

    * var: 'prefix'='jak'
    * verify: '$.username' starts with: "${prefix}i"
    * verify: ${username} starts with: "${prefix}i"

  Scenario: ends with
    * uri: /users/me
    * send: GET
    * status: 200
    * verify: '$.username'='jakim'
    * verify: '$.username' ends with: 'kim'

    * var: 'username'="jakim"
    * verify: ${username} ends with: 'kim'

    * var: 'suffix'='kim'
    * verify: '$.username' ends with: "ja${suffix}"
    * verify: ${username} ends with: "ja${suffix}"
