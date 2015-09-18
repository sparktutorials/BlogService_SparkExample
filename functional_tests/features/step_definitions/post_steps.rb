#encoding: utf-8

require 'rest-client'
require 'json'
require "rspec"
include RSpec::Matchers


# psql -h 127.0.0.1 -p 7500 -U blog_owner -d blog

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
