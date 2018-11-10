Feature: open browser
  open a headless browser and then go to a page

  Background:
    * base uri: http://localhost:12306

  Scenario: open browser and go to a page
    * open: /index.html
    * verify: '#current' text: 'index.html'
    * close
