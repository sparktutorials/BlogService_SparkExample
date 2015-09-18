#encoding: utf-8

require 'rest-client'
require 'json'
require "rspec"
include RSpec::Matchers

# psql -h 127.0.0.1 -p 7500 -U blog_owner -d blog

def execute_sql(sql_code)
    done = system "sh db_execute.sh \"#{sql_code}\""
    raise Exception.new("Issue executing sql code: #{sql_code}") unless done
end

#
# Given
#

Given(/^that on the DB there is a post with UUID=([a-f0-9-]+) title="([^"]*)" content="([^"]*)"$/) do |uuid, title, content|
  execute_sql("insert into posts(post_uuid, title, content) values ('#{uuid}', '#{title}', '#{content}');")
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

When(/^I edit post ([a-f0-9-]+) setting title="([^"]*)"$/) do |uuid, title|
  payload = """
  {
    \"title\" : \"#{title}\"
  }
  """
  STDOUT.puts("URL = " + "http://localhost:4567/posts/#{uuid}")
  response = RestClient.put "http://localhost:4567/posts/#{uuid}", payload, :content_type => :json, :accept => :json
  expect(response.code).to eq(200)
end

When(/^I edit post ([a-f0-9-]+) setting content="([^"]*)"$/) do |uuid, content|
  payload = """
  {
    \"content\" : \"#{content}\"
  }
  """
  response = RestClient.put "http://localhost:4567/posts/#{uuid}", payload, :content_type => :json, :accept => :json
  expect(response.code).to eq(200)
end

When(/^I delete post ([a-f0-9-]+)$/) do |uuid|
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

Then(/^the post ([a-f0-9-]+) has title "([^"]*)"$/) do |uuid, title|
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^the post ([a-f0-9-]+) has content "([^"]*)"$/) do |uuid, content|
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^post ([a-f0-9-]+) is not found$/) do |uuid|
  pending # Write code here that turns the phrase above into concrete actions
end