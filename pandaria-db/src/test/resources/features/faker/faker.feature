Feature: faker usage in SQL
  use faker in sql

  Background:
    * base uri: http://localhost:10080
    * dir: features/faker
    * execute sql: classpath:features/database/drop_table.sql
    * execute sql: classpath:features/database/setup.sql

  # http://dius.github.io/java-faker/apidocs/index.html
  Scenario: faker in sql as doc string
    * execute sql:
    """
    INSERT INTO USERS(NAME, AGE)
    VALUES('#{name.full_name}', #{number.number_between '1','100'});
    """
    * query:
    """
    SELECT * FROM USERS
    """
    # there is already one record inserted in setup.sql, so here it gets two
    * verify: '$.length()'=2
