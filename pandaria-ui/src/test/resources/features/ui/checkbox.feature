Feature: html checkbox
  operations about html checkbox

  Background:
    * base uri: http://localhost:12306/local

  Scenario: check/uncheck html checkbox
    * open: pages/checkbox.html
    * verify: 'input[name='volvo']' unchecked
    * verify: 'input[name='saab']' unchecked
    * verify: 'input[name='vw']' unchecked
    * verify: 'input[name='audi']' unchecked

    * check: 'input[name='volvo']'
    * check: 'input[name='saab']'
    * check: 'input[name='vw']'
    * check: 'input[name='audi']'

    * verify: 'input[name='volvo']' checked
    * verify: 'input[name='saab']' checked
    * verify: 'input[name='vw']' checked
    * verify: 'input[name='audi']' checked

    * uncheck: 'input[name='volvo']'
    * uncheck: 'input[name='saab']'
    * uncheck: 'input[name='vw']'
    * uncheck: 'input[name='audi']'

    * verify: 'input[name='volvo']' unchecked
    * verify: 'input[name='saab']' unchecked
    * verify: 'input[name='vw']' unchecked
    * verify: 'input[name='audi']' unchecked
