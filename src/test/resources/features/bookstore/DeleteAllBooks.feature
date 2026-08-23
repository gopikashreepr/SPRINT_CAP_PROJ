Feature: BookStore - Delete All Books
  As an authenticated user
  I want to clear my entire book collection
  So that I can start fresh

  Background:
    Given the BookStore API base URL is configured
    And a user already exists with a unique username and a strong password
    And I have generated a valid token for that user
    And ISBN "isbn1" has been added to the user's collection

  @BookStore @Delete @Positive @Smoke @Chaining
  Scenario: Delete all books from the user's collection using a valid token
    When I delete all books from the user's collection using the saved token
    Then the response status code should be 204

  @BookStore @Delete @Negative
  Scenario: Delete all books without an authorization token
    When I delete all books from the user's collection without a token
    Then the response status code should be 401
