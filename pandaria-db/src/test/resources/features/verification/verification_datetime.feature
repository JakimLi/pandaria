Feature: verify date time
  verification based on date time

  Background:
    * dir: features/verification
    * execute sql: classpath:features/database/all_data_types.sql

  Scenario: equals
    * query:
    """
    select `date`, `datetime`, `timestamp`, `time` from all_data_types;
    """
    * verify: '$[0].date'=datetime: '2008-10-10' pattern: 'yyyy-MM-dd'
    * verify: '$[0].datetime'=datetime: '2008-08-08 10:30:30' pattern: 'yyyy-MM-dd hh:mm:ss'
    * verify: '$[0].timestamp'=datetime: '2008-01-01 00:00:01' pattern: 'yyyy-MM-dd HH:mm:ss'
    * verify: '$[0].timestamp'=datetime: '2008-01-01 00:00:01+0800' pattern: 'yyyy-MM-dd HH:mm:ssZ'
    * verify: '$[0].time'=datetime: '10:30:10' pattern: 'hh:mm:ss'

  Scenario: before
    * query:
    """
    select `date`, `datetime`, `timestamp`, `time` from all_data_types;
    """
    * verify: '$[0].date' before: datetime: '2008-10-11' pattern: 'yyyy-MM-dd'
    * verify: '$[0].date' before: datetime: '11/10/2008' pattern: 'dd/MM/yyyy'
    * verify: '$[0].datetime' before: datetime: '2008-08-08 10:30:31' pattern: 'yyyy-MM-dd hh:mm:ss'
    * verify: '$[0].timestamp' before: datetime: '2008-01-01 00:00:02' pattern: 'yyyy-MM-dd HH:mm:ss'
    * verify: '$[0].timestamp' before: datetime: '2008-01-01 00:00:02+0800' pattern: 'yyyy-MM-dd HH:mm:ssZ'
    * verify: '$[0].time' before: datetime: '10:30:11' pattern: 'hh:mm:ss'

  Scenario: after
    * query:
    """
    select `date`, `datetime`, `timestamp`, `time` from all_data_types;
    """
    * verify: '$[0].date' after: datetime: '2008-10-09' pattern: 'yyyy-MM-dd'
    * verify: '$[0].date' after: datetime: '09/10/2008' pattern: 'dd/MM/yyyy'
    * verify: '$[0].datetime' after: datetime: '2008-08-08 10:30:29' pattern: 'yyyy-MM-dd hh:mm:ss'
    * verify: '$[0].timestamp' after: datetime: '2008-01-01 00:00:00' pattern: 'yyyy-MM-dd HH:mm:ss'
    * verify: '$[0].timestamp' after: datetime: '2008-01-01 00:00:00+0800' pattern: 'yyyy-MM-dd HH:mm:ssZ'
    * verify: '$[0].time' after: datetime: '10:30:09' pattern: 'hh:mm:ss'
