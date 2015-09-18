#encoding: utf-8

Feature: Post managament
    I can create posts
    I can edit posts
    I can delete posts
  
    Scenario: Add a post
        When I insert a post with title "Foo" and content "bar"
        Then I have 1 posts
        Then the post has title "Foo"
        Then the post has content "bar"
