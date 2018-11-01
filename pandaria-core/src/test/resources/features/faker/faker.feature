@faker
Feature: faker usage in variable
  Assign fake data to a variable, so that it can be used for test, or directly put it in requests/sql

  Background:
    * base uri: http://localhost:10080

  # http://dius.github.io/java-faker/apidocs/index.html
  Scenario: assign it to variable
    * var: 'name'=faker: #{name.first_name}
    * verify code: "${name}".length > 0

    * var: 'full_name'=faker: #{name.full_name}
    * verify code: "${full_name}".length > 0

  Scenario: use it in request body as doc string
    * uri: /faker/users
    * request body:
    """
    {"name": "#{Name.fullName}", "city": "#{Address.city}"}
    """
    * send: POST
    * response body:
    """
    success
    """
