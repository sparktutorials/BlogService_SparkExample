#encoding: utf-8

require 'rest-client'
require 'json'
require "rspec"
include RSpec::Matchers

# psql -h 127.0.0.1 -p 7500 -U blog_owner -d blog

#
# Given
#

Given(/^that on the DB there is a post with UUID=(\d+)ff(\d+)\-(\d+)e\-(\d+)\-a(\d+)\-(\d+)ef(\d+) title="([^"]*)" content="([^"]*)"$/) do |arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9|
  pending # Write code here that turns the phrase above into concrete actions
end

#
# When
#

When(/^I insert a post with title "([^"]*)" and content "([^"]*)"$/) do |title, content|
  payload = """
  {
    \"title\" : \"#{title}\",
    \"content\" : \"#{content}\",
    \"categories\" : []
  }
  """
  response = RestClient.post 'http://localhost:4567/posts', payload, :content_type => :json, :accept => :json
  expect(response.code).to eq(201)
end

When(/^I edit post (\d+)ff(\d+)\-(\d+)e\-(\d+)\-a(\d+)\-(\d+)ef(\d+) setting title="([^"]*)"$/) do |arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8|
  pending # Write code here that turns the phrase above into concrete actions
end

When(/^I edit post (\d+)ff(\d+)\-(\d+)e\-(\d+)\-a(\d+)\-(\d+)ef(\d+) setting content="([^"]*)"$/) do |arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8|
  pending # Write code here that turns the phrase above into concrete actions
end

When(/^I delete post (\d+)ff(\d+)\-(\d+)e\-(\d+)\-a(\d+)\-(\d+)ef(\d+)$/) do |arg1, arg2, arg3, arg4, arg5, arg6, arg7|
  pending # Write code here that turns the phrase above into concrete actions
end

#
# Then
#

Then(/^I have (\d+) posts?$/) do |n_posts|
    begin
      response = RestClient.get 'http://localhost:4567/posts'      
      expect(response.code).to eq(200)
      data = JSON.parse(response.body)
      expect(data.count).to eq(n_posts.to_i)
    rescue RestClient::InternalServerError => e
        STDERR.puts (e.methods)
        throw e
    end
end

Then(/^the post has title "([^"]*)"$/) do |title|
    begin
      response = RestClient.get 'http://localhost:4567/posts'      
      expect(response.code).to eq(200)
      data = JSON.parse(response.body)
      expect(data[0]["title"]).to eq(title)
    rescue RestClient::InternalServerError => e
        STDERR.puts (e.methods)
        throw e
    end
end

Then(/^the post has content "([^"]*)"$/) do |content|
    begin
      response = RestClient.get 'http://localhost:4567/posts'      
      expect(response.code).to eq(200)
      data = JSON.parse(response.body)
      expect(data[0]["content"]).to eq(content)
    rescue RestClient::InternalServerError => e
        STDERR.puts (e.methods)
        throw e
    end
end

Then(/^the post (\d+)ff(\d+)\-(\d+)e\-(\d+)\-a(\d+)\-(\d+)ef(\d+) has title "([^"]*)"$/) do |arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8|
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^the post (\d+)ff(\d+)\-(\d+)e\-(\d+)\-a(\d+)\-(\d+)ef(\d+) has content "([^"]*)"$/) do |arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8|
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^post (\d+)ff(\d+)\-(\d+)e\-(\d+)\-a(\d+)\-(\d+)ef(\d+) is not found$/) do |arg1, arg2, arg3, arg4, arg5, arg6, arg7|
  pending # Write code here that turns the phrase above into concrete actions
end