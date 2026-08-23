Feature: BookStore - Replace a Book
  As an authenticated user
  I want to swap one book in my collection for another
  So that I can correct or update my reading list

  Background:
    Given the BookStore API base URL is configured
    And a user already exists with a unique username and a strong password
    And I have generated a valid token for that user
    And ISBN "isbn1" has been added to the user's collection

  @BookStore @Update @Positive @Smoke @Chaining
  Scenario: Replace an existing book with a new valid ISBN using a valid token
    When I replace ISBN "isbn1" with ISBN "isbn2" using the saved token
    Then the response status code should be 200
    And the response should match the "user-schema.json" schema

  @BookStore @Update @Negative
  Scenario: Replace a book without an authorization token
    When I replace ISBN "isbn1" with ISBN "isbn2" without a token
    Then the response status code should be 401
