#!/usr/bin/env sh
DATABASE_URL=$(heroku config:get DATABASE_URL)
java -jar build/libs/VisArtApplication-0.0.1-SNAPSHOT.jar