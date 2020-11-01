ECHO off


:: A basic curl request ->
:: curl -s -X GET http://localhost:8080/managers/
:: OR THIS FOR POST ->
:: curl -s -X POST http://localhost:8000/managers/ {"name":"bob"}

:: This is an assertion example ->
:: curl -s -X GET http://localhost:8080/ | findstr "string to find"
:: if not errorlevel 1 (
::     echo success: connects to localhost:8080
:: ) else (
::     echo failed: localhost:8080 not available
:: )

curl -s -X GET http://localhost:8080/ | findstr "Vis"
if not errorlevel 1 (
    echo success: connects to localhost:8080
) else (
    echo failed: localhost:8080 not available
)

curl -s -X POST http://localhost:8080/create_manager --data '{"idCode":"16324123323423","emailAddress":"auryan898@gmail.com","displayName":"dingdong","userName":"hiIAmBilly","ProfilePicLink":"","profileDescription":""}'


echo on