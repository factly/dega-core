Feature: Create Read Update Delete and Search Tags

    Scenario: Create a Tag
        Given Create a Tag
        When the tag object is valid
        Then the result code is 200
