@ignore
Feature: open browser
  open a headless browser and then go to a page

  Background:
    * base uri: http://locahost:10080

  Scenario: open browser and go to a page
    * open: /index.html
#    * close
