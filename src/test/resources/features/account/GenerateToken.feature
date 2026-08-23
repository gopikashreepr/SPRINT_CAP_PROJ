Feature: Account - Generate Token
  As an API consumer
  I want to authenticate and obtain a bearer token
  So that I can perform authorized operations against BookStore endpoints

  Background:
    Given the BookStore API base URL is configured
    And a user already exists with a unique username and a strong password

  @Account @Auth @Positive @Smoke @Chaining
  Scenario: Generate a token with valid credentials
    When I request a token for the existing user
    Then the response status code should be 200
    And the response should match the "token-schema.json" schema
    And the response status field should be "Success"
    And the token should be saved for later steps

  @Account @Auth @Negative
  Scenario: Generate a token with an incorrect password
    When I request a token using an incorrect password
    Then the response status code should be 200
    And the response status field should be "Failed"
