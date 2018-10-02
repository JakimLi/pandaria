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
    * verify: '$[0].varchar'='varchar'

    * query:
    """
    select `tinyint` from all_data_types;
    """
    * verify: '$[0].tinyint'=3
    * verify: '$[0].tinyint'>2
    * verify: '$[0].tinyint'<4
    * verify: '$[0].tinyint'>=3
    * verify: '$[0].tinyint'<=3

    * query:
    """
    select `text` from all_data_types;
    """
    * verify: '$[0].text'='text'

    * query:
    """
    select `date` from all_data_types;
    """
    * verify: '$[0].date'=date: '2008-10-10' pattern: 'yyyy-MM-dd'
    * verify: '$[0].date'=date: '10/10/2008+0800' pattern: 'dd/MM/yyyyZ'
