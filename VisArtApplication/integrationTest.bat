ECHO off

::Using a custom java jar to create http requests ->
::java -jar ..\JavaHttpRequests\build\libs\JavaHttpRequests.jar POST google.com / {"pie":"data"}
set "http=java -jar ..\JavaHttpRequests\build\libs\JavaHttpRequests.jar"

%http% GET localhost:8080 /
if not errorlevel 1 (
    echo success: connects to localhost:8080
) else (
    echo failed: localhost:8080 not available
)

ECHO on