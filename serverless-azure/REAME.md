# Azure Functions demo with Java

## Build
`mvn clean package`

## Run locally
`mvn azure-functions:run`

## Depoloy
```
az login
mvn azure-functions:deploy
```

## Documentation
[Reference documentation](https://docs.microsoft.com/en-us/azure/azure-functions/functions-reference-java)

## Curl
### Ping
Locally:
```
curl http://localhost:7071/api/ping?name=foo
```

Remotely:
```
curl https://50shadesofserverless.azurewebsites.net/api/ping?name=foo
```

### Transform
Locally:
```
curl --header "Content-Type: application/octet-stream" --data-binary @input.xml http://localhost:7071/api/transform
```
Remotely:
```
curl --header "Content-Type: application/octet-stream" --data-binary @input.xml https://50shadesofserverless.azurewebsites.net/api/transform
```

NOTE! For `byte[]` parameters, `--header "Content-Type: application/octet-stream"` us crucial.

