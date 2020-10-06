SETLOCAL
for /f "delims=" %%A in ('heroku config:get DATABASE_URL -a vis-art-application') do set "DATABASE_URL=%%A"
for /f "delims=" %%A in ('heroku run echo "$JDBC_DATABASE_URL"') do set "JDBC_DATABASE_URL=%%A"
for /f "delims=" %%A in ('heroku run echo "$JDBC_DATABASE_USERNAME"') do set "JDBC_DATABASE_USERNAME=%%A"
for /f "delims=" %%A in ('heroku run echo "$JDBC_DATABASE_PASSWORD"') do set "JDBC_DATABASE_PASSWORD=%%A"
for /f "delims=" %%A in ('heroku run echo "$SPRING_DATASOURCE_URL"') do set "SPRING_DATASOURCE_URL=%%A"
for /f "delims=" %%A in ('heroku run echo "$SPRING_DATASOURCE_USERNAME"') do set "SPRING_DATASOURCE_USERNAME=%%A"
for /f "delims=" %%A in ('heroku run echo "$SPRING_DATASOURCE_PASSWORD"') do set "SPRING_DATASOURCE_PASSWORD=%%A"
java -jar build\libs\VisArtApplication-0.0.1-SNAPSHOT.jar
SETLOCAL