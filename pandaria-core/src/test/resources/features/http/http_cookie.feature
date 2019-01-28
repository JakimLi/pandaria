@http
Feature: Http feature
  Basic http operations with verifications

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  Scenario: add cookie for request
    * uri: /cookie
    * cookie: 'key'='value'
    * send: get
    * response body:
    """
    cookie added
    """
    
    * uri: /cookie
    * var: val="value"
    * cookie: 'key'="${val}"
    * send: get
    * response body:
    """
    cookie added
    """

  Scenario: read response cookie value
    * uri: /mock_login
    * send: POST
    * var: jsession<-cookie:'SessionId'
    * verify: ${jsession}='ABCDEFG'
