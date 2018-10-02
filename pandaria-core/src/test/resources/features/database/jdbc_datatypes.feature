@database
Feature: jdbc data types
  test if we can handle different kinds of jdbc data types
  https://www.cis.upenn.edu/~bcpierce/courses/629/jdkdocs/guide/jdbc/getstart/mapping.doc.html

  Background:
    * dir: features/database

  Scenario: different db types
    * execute sql: all_data_types.sql

    * query:
    """
    select `varchar` from all_data_types;
    """
    * verify: '$[0].varchar'='test'

    * query:
    """
    select `tinyint` from all_data_types;
    """
    * verify: '$[0].tinyint'=3
    * verify: '$[0].tinyint'>2
    * verify: '$[0].tinyint'<4
    * verify: '$[0].tinyint'>=3
    * verify: '$[0].tinyint'<=3
