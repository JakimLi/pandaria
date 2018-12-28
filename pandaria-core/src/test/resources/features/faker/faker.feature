@faker
Feature: faker usage in http request and response
  Assign fake data to a variable, so that it can be used for test, or directly put it in requests

  Background:
    * base uri: http://localhost:10080
    * dir: features/faker

  # http://dius.github.io/java-faker/apidocs/index.html
  Scenario: assign it to variable
    * var: name=faker: #{name.first_name}
    * verify code: "${name}".length > 0

    * var: full_name=faker: #{name.full_name}
    * verify code: "${full_name}".length > 0

  Scenario: faker in request body as doc string
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

  Scenario: faker in request body as file
    * uri: /faker/users
    * request body: requests/user.json
    * send: POST
    * response body:
    """
    success
    """

  Scenario: faker locale
    * faker locale: zh-CN
    * var: name=faker: #{name.full_name}
    * verify: ${name} matches: '\p{sc=Han}*'

  Scenario: escape with ##{}
    * uri: /faker/users/escape
    * request body:
    """
    {"name": "#{Name.fullName}", "city": "##{Address.city}"}
    """
    * send: POST
    * response body:
    """
    success
    """
