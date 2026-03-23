Feature: Login Functionality
  As a user
  I want to login to the application
  So that I can access the secure area or see an error message for invalid credentials

  @smoke
  Scenario: Valid login
    Given user is on login page
    When user enters username and password
    And clicks login button
    Then user should see dashboard

  @regression
  Scenario: Invalid login
    Given user is on login page
    When user enters invalid credentials
    And clicks login button
    Then user should see error message