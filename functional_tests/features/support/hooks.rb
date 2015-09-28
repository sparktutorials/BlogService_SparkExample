#encoding: utf-8

require 'rest-client'

VERBOSE = false

def create_clean_db
    started = system 'sh start_db.sh >/dev/null &'
    raise Exception.new('Unable to start DB') unless started
    attempts_left = 30
    while attempts_left > 0
        up_and_running = system 'sh db_is_up.sh'
        return if up_and_running
        puts "Waiting for db... (attemps left #{attempts_left})" if $VERBOSE
        sleep(1)
        attempts_left = attempts_left - 1
    end
    stop_db
    raise Exception.new('DB does not respond, giving up')
end

def stop_db
    system 'sh kill_all_functests_db_containers.sh'
end

def application_up_and_running?
    begin
        RestClient.get 'http://localhost:4567/alive'
        return true
    rescue Exception => e
        $stdout.puts "Error #{e}"
        return false
    end
end

def start_application
    res = system 'sh start_application.sh'    
    attempts_left = 30
    while attempts_left > 0
        up_and_running = application_up_and_running?
        return if up_and_running
        $stdout.puts "Waiting for the application... (attemps left #{attempts_left})" if $VERBOSE
        sleep(2)
        attempts_left = attempts_left - 1
    end
    stop_application
    raise Exception.new('The application does not respond, giving up')
end

def stop_application
    res = system 'sh stop_application.sh'
end

Before do |scenario|
    # in case there are leftovers
    stop_application
    stop_db

    create_clean_db
    start_application
end

After do |scenario|
    stop_application
    stop_db
end