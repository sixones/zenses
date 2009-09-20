cd zenses-lib/
call mvn clean test install
cd ../zenses/
call mvn clean install assembly:assembly
cd ..
