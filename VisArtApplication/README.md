# VisArtApplication Developer Readme

At this point, this project can build using gradle, it includes the Spring Boot
and Hibernate dependencies, and dependencies required to access its related
Heroku database instance.

## How to Build and Run Locally

You can build the application on any device by `gradlew build` or `gradlew.bat build` on Windows.

To Run this web server use the following commands for Linux & Mac:

    DATABASE_URL=$(heroku config:get DATABASE_URL)
    java -jar build/libs/VisArtApplication-*.jar

Or you can run `bash run-local-spring.sh` or `bash run-local-spring-anyone.sh` if you don't have heroku configured.

---

BUT ON WINDOWS, you can use `run-local-spring.bat` or `run-local-spring-anyone.bat` if you don't have heroku configured.  

Note: the commands for Windows is weird and confusing, though it can be found in `run-local-spring.bat`.

## Deploying Code

Currently, it AUTOMATICALLY deploys when this code is pushed to `auryan898/ecse321-vis-art-fork`. In the future, this will be changed to the main repo: `McGill-ECSE321-Fall2020/project-group-05`


This code can also be MANUALLY deployed by the commands:

    heroku git:remote -a vis-art-application # Just do this command once ever to setup
    git push heroku branch-name:master # use this to push the code from a local branch to the master on heroku

This `vis-art-application` on heroku is good for development/staging at the moment, and there will be a production application later on.