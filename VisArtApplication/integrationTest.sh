curl -s -X GET http://localhost:8080/
if [ $? -eq 0 ]
then
  echo "success: connects to localhost:8080"
else
  echo "failure: cannot connect to localhost"
  curl -s -X GET https://vis-art-application.herokuapp.com/
fi