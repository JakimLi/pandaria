@http_global_headers
Feature: Be able to set global headers
  Be able to set global header from properties file

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  Scenario: global header
    * uri: /global_header
    * header: 'will_be_overrided'='overrided'
    * send: GET
    * status: 200
    * response body:
    """
    global header added
    """
