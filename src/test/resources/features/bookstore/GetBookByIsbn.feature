Feature: BookStore - Get Book by ISBN
  As an API consumer
  I want to look up a single book by its ISBN
  So that I can view its details

  Background:
    Given the BookStore API base URL is configured

  @BookStore @Read @Positive @Smoke
  Scenario: Get a book using a valid ISBN
    When I request the book with ISBN "isbn1"
    Then the response status code should be 200
    And the response should match the "book-schema.json" schema

  @BookStore @Read @Negative
  Scenario: Get a book using an ISBN that does not exist
    When I request the book with ISBN "invalidIsbn"
    Then the response status code should be 400

  @BookStore @Read @Boundary
  Scenario: Get a book using an empty ISBN query parameter
    When I request the book with an empty ISBN
    Then the response status code should be 400
