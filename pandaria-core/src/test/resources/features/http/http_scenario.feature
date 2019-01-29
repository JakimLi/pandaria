@http
Feature: Http feature with customized cookie and request header
  Set default cookies and request headers for all uri of a scenario.

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  @DefaultCookieAndHeader
  Scenario: set default cookies and headers by Java code
    * uri: /only_for_certain_header_and_cookie
    * send: get
    * response body:
    """
    cookie and header is successfully set by Java code.
    """
