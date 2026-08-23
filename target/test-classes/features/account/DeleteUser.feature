Feature: Account - Delete User
  As an API consumer
  I want to delete a user account
  So that account cleanup can be verified

  Background:
    Given the BookStore API base URL is configured
    And a user already exists with a unique username and a strong password
    And I have generated a valid token for that user

  @Account @Delete @Positive @Smoke @Chaining
  Scenario: Delete an existing user with a valid token
    When I send a request to delete the saved user using the saved token
    Then the response status code should be 204

  @Account @Delete @Negative
  Scenario: Delete a user without an authorization token
    When I send a request to delete the saved user without a token
    Then the response status code should be 401
