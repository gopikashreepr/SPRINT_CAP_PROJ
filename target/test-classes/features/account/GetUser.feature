Feature: Account - Get User
  As an API consumer
  I want to retrieve user details by userId
  So that I can verify account information

  Background:
    Given the BookStore API base URL is configured
    And a user already exists with a unique username and a strong password
    And I have generated a valid token for that user

  @Account @Read @Positive @Smoke @Chaining
  Scenario: Get an existing user's details using a valid token
    When I request the user details using the saved userId and token
    Then the response status code should be 200
    And the response should match the "user-schema.json" schema

  @Account @Read @Negative
  Scenario: Get user details without an authorization token
    When I request the user details using the saved userId but no token
    Then the response status code should be 401
