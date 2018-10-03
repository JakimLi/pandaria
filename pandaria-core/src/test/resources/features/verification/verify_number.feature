@verify_string
@http
Feature: verify numbers
  verify numbers

  Background:
    * dir: features/verification
    * base uri: http://localhost:10080

  Scenario: integer equals
    * uri: /users/me
    * send: GET
    * status: 200
    * verify: '$.age'=18
    * verify: '$.iq'=double: 80.0

    * var: 'age'=18
    * var: 'iq'=80.0

    * verify: ${age}!=19
    * verify: ${iq}!=89.0
    * verify: ${age}!=${iq}

    * verify: '$.age'!=19
    * verify: '$.iq'!=double: 89.0

  Scenario: greater than
    * uri: /users/me
    * send: GET
    * status: 200
    * verify: '$.age'>17
    * verify: '$.iq'>double: 70.0
    * verify: '$.age'>=18
    * verify: '$.iq'>=double: 80.0

    * var: 'age'=18
    * var: 'iq'=80.0

    * verify: ${age}>17
    * verify: ${iq}>79.0

    * verify: ${age}>=17
    * verify: ${iq}>=80.0

  Scenario: less than
    * uri: /users/me
    * send: GET
    * status: 200
    * verify: '$.age'<19
    * verify: '$.iq'<double: 90.0
    * verify: '$.age'<=18
    * verify: '$.iq'<=double: 80.0

    * var: 'age'=18
    * var: 'iq'=80.0

    * verify: ${age}<19
    * verify: ${iq}<99.0

    * verify: ${age}<=18
    * verify: ${iq}<=80.0
