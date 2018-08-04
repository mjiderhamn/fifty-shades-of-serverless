## Run locally
```
mvn clean package
mvn azure-functions:run
```

## Test locally
```
curl -H "Content-Type: application/octet-stream" localhost:7071/api/transform --data-binary @input.xml
```

TODO "Cannot locate the method signature with the given input"