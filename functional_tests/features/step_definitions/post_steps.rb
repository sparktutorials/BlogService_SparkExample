#encoding: utf-8

# psql -h 127.0.0.1 -p 7500 -U blog_owner -d blog

def create_clean_db
    started = system 'sh start_db'
    raise Exception('Unable to start DB') unless started
    attempts_left = 30
    while attempts_left > 0
        up_and_running = system 'sh db_is_up.sh'
        return if up_and_running
        puts "Waiting for db... (attemps left #{attempts_left}"
        sleep(1)
        attempts_left = attempts_left - 1
    end
    stop_db
    raise Exception('DB does not respond, giving up')
end

def stop_db
    system 'sh kill_all_functests_db_containers.sh'
end

def application_up_and_running?
    begin
        RestClient.get 'http://localhost:4567/alive'
        return true
    rescue
        return false
    end
end

def start_application
    res = system 'sh start_application.sh'    
    attempts_left = 30
    while attempts_left > 0
        up_and_running = application_up_and_running?
        return if up_and_running
        puts "Waiting for the application... (attemps left #{attempts_left}"
        sleep(2)
        attempts_left = attempts_left - 1
    end
    stop_application
    raise Exception('The application does not respond, giving up')
end

def stop_application
    res = system 'sh stop_application.sh'
end

Given(/^an empty blog$/) do
  create_clean_db
  start_application
end

When(/^I insert a post with title "([^"]*)" and body "([^"]*)"$/) do |arg1, arg2|
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^I have one post$/) do
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^the post has ID (\d+)$/) do |arg1|
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^the post has title "([^"]*)"$/) do |arg1|
  pending # Write code here that turns the phrase above into concrete actions
end

Then(/^the post has body "([^"]*)"$/) do |arg1|
  pending # Write code here that turns the phrase above into concrete actions
end
