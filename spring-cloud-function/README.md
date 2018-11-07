## Run locally
```
mvn spring-boot:run
```

## Test locally
```
curl -H "Content-Type: text/plain" localhost:8080 -d World
```

### Transform
Locally:
```
curl -s --header "Content-Type: application/octet-stream" --data-binary @input.xml http://localhost:8080
```
