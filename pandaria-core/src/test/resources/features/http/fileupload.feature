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

  Scenario: upload file with form data
    * form: /form
    * field: name value:
    """
    lj
    """
    * field: data value:
    """
    {"name": "lj", "age", 18}
    """
    * field: user value: requests/user.json
    * field: file attachment: attachments/abc.txt
    * send: POST
    * status: 200
    * response body:
    """
    uploaded
    """
