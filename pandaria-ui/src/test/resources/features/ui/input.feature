Feature: html input
  operations about input

  Background:
    * base uri: http://localhost:12306/local

  Scenario: fill in text input
    * open: pages/register.html
    * verify: 'input[name='email']' attribute: 'value' equals: ''

    * input: 'input[name='email']' text: 'lj@gmail.com'
    * verify: 'input[name='email']' attribute: 'value' equals: 'lj@gmail.com'

    * close
