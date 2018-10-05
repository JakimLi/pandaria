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
    select `date`, `datetime`, `timestamp`, `time` from all_data_types;
    """
    * verify: '$[0].date'=datetime: '2008-10-10' pattern: 'yyyy-MM-dd'
    * verify: '$[0].datetime'=datetime: '2008-08-08 10:30:30' pattern: 'yyyy-MM-dd hh:mm:ss'
    * verify: '$[0].timestamp'=datetime: '2008-01-01 00:00:01' pattern: 'yyyy-MM-dd HH:mm:ss'
    * verify: '$[0].time'=datetime: '10:30:10' pattern: 'hh:mm:ss'

    * query:
    """
    select `smallint` from all_data_types;
    """
    * verify: '$[0].smallint'=30

    * query:
    """
    select `mediumint`, `int` from all_data_types;
    """
    * verify: '$[0].mediumint'=3000000
    * verify: '$[0].int'=3

    * query:
    """
    select `bigint` from all_data_types;
    """
    * verify: '$[0].bigint'=long: 3000000000
    * verify: '$[0].bigint'>long: 2000000000
    * verify: '$[0].bigint'>=long: 3000000000
    * verify: '$[0].bigint'<long: 4000000000

    * query:
    """
    select `float`, `double` from all_data_types;
    """
    * verify: '$[0].float'=float: 10.2
    * verify: '$[0].float'>float: 10.1
    * verify: '$[0].float'>=float: 10.2
    * verify: '$[0].float'<float: 10.3
    * verify: '$[0].float'<=float: 10.2

    * verify: '$[0].double'=double: 10.201802
    * verify: '$[0].double'>double: 10.101802
    * verify: '$[0].double'>=double: 10.201802
    * verify: '$[0].double'<double: 10.301802
    * verify: '$[0].double'<=double: 10.201802

    * query:
    """
    select `decimal` from all_data_types;
    """
    * verify: '$[0].decimal'=decimal: 400000.0
    * verify: '$[0].decimal'>decimal: 300000.0
    * verify: '$[0].decimal'>=decimal: 400000.0
    * verify: '$[0].decimal'<decimal: 500000.0
    * verify: '$[0].decimal'<=decimal: 500000.0

    * query:
    """
    select `bool` from all_data_types;
    """
    * verify: '$[0].bool'=true
