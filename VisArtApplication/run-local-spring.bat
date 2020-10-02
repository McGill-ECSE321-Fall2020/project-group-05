SETLOCAL
for /f "delims=" %%A in ('heroku config:get DATABASE_URL -a vis-art-application') do set "DATABASE_URL=%%A"
java -jar build\libs\VisArtApplication-0.0.1-SNAPSHOT.jar
SETLOCAL