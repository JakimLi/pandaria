Feature: faker usage in variable
  Assign fake data to a variable, so that it can be used for test

  # http://dius.github.io/java-faker/apidocs/index.html
  # remove the first faker and all parentheses
  Scenario: faker name
    * var: 'name'=faker: #{name.first_name}
    * verify code: "${name}".length > 0

    * var: 'full_name'=faker: #{name.full_name}
    * verify code: "${full_name}".length > 0
