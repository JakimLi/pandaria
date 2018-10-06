Feature: bad ssl
  make sure bad ssl can work, this interacts with https://self-signed.badssl.com/ and https://badssl.com/

  Background:
    * dir: features/ssl

  Scenario: self signed
    * uri: https://self-signed.badssl.com/
    * send: GET
    * status: 200
