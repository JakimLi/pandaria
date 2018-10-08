@file_upload
Feature: file upload
  be able to upload file

  Background:
    * dir: features/http
    * base uri: http://localhost:10080

  Scenario: upload file
    * uri: /files
    * attachment: attachments/abc.txt
    * send: POST
    * status: 200
    * response body:
    """
    uploaded
    """
