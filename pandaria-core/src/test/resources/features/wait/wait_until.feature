@wait_until
Feature: wait until
  wait and retry until the verification pass or failing on max retry time exceeds

  Background:
    * dir: features/wait
    * base uri: http://localhost:10080

  Scenario: wait until
    * wait: 1000ms times: 3
    * uri: /sequence
    * send: GET
    * response body:
    """
    3
    """

    * uri: /sequence
    * send: GET
    * response body:
    """
    4
    """

    * wait: 1000ms times: 3
    * uri: /sequence
    * send: GET
    * response body:
    """
    5
    """

    * uri: /sequence
    * send: GET
    * response body:
    """
    6
    """
