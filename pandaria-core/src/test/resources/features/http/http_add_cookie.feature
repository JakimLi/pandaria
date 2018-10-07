@http
Feature: Http feature
  Basic http operations with verifications

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  Scenario: add cookie
    * uri: /cookie
    * cookie: 'key'='value'
    * send: get
    * response body:
    """
    cookie added
    """
    
    * uri: /cookie
    * var: 'val'="value"
    * cookie: 'key'="${value}"
    * send: get
    * response body:
    """
    cookie added
    """
