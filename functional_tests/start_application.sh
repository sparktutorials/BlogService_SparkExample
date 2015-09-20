mvn -f ../pom.xml exec:java -Dexec.args="--db-port 7500" > log_app_out.txt 2> log_app_err.txt &
echo $! > .saved_pid

