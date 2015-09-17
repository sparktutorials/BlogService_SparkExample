#encoding: utf-8

Feature: Post managament
    I can create posts
    I can edit posts
    I can delete posts
  
    Scenario: Add a post
        Given an empty blog
        When I insert a post with title "Foo" and body "bar"
        Then I have one post
        Then the post has ID 1
        Then the post has title "Foo"
        Then the post has body "bar"
