Feature: common feature
  common operations that applies to all html element

  Background:
    * base uri: http://localhost:12306/local

  Scenario: verify html element show or hidden
    * open: pages/common.html

    * verify: 'input[name='shown']' display
    * verify: 'input[name='hidden']' hidden
    * verify: 'input[name='invisible']' hidden

  Scenario: verify html element enabled or disabled
    * open: pages/common.html

    * verify: 'input[name='enabled']' enabled
    * verify: 'input[name='disabled']' disabled
