ECHO off

::Using a custom java jar to create http requests ->
::java -jar ..\JavaHttpRequests\build\libs\JavaHttpRequests.jar POST google.com / {"pie":"data"}
set "http=java -jar ..\JavaHttpRequests\build\libs\JavaHttpRequests.jar"

%http% GET localhost:8080 /
if not errorlevel 1 (
    echo success: connects to localhost:8080
    set "http_get=java -jar ..\JavaHttpRequests\build\libs\JavaHttpRequests.jar GET localhost:8080"
    set "http_post=java -jar ..\JavaHttpRequests\build\libs\JavaHttpRequests.jar POST localhost:8080"
) else (
    echo failed: localhost:8080 not available
    set "http_get=java -jar ..\JavaHttpRequests\build\libs\JavaHttpRequests.jar GET vis-art-application.herokuapp.com"
    set "http_post=java -jar ..\JavaHttpRequests\build\libs\JavaHttpRequests.jar POST vis-art-application.herokuapp.com"
)

%http_get% /users/get_all

pause
ECHO on