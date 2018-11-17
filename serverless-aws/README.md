# AWS Lambda examples

## Maven archetype for generating new project
See [here](https://github.com/awslabs/aws-serverless-java-archetype)
```
mvn archetype:generate -DarchetypeGroupId=com.amazonaws.serverless.archetypes -DarchetypeArtifactId=aws-serverless-java-archetype -DarchetypeVersion=1.0.0 -DarchetypeRepository=local
```

## Deploy
Preparations
```
aws configure
aws s3 mb s3://fifty-shades --region eu-central-1
```

Using Maven
```
mvn deploy
```

Manually
```
sam package --template-file sam.yaml --output-template-file serverless-output.yaml --s3-bucket fifty-shades
sam deploy --template-file serverless-output.yaml --stack-name fifty-shades-of-serverless --capabilities CAPABILITY_IAM

# Should be same as 
# aws cloudformation package --template-file template.yaml --output-template-file serverless-output.yaml --s3-bucket fifty-shades 
# aws cloudformation deploy --template-file serverless-output.yaml --stack-name fifty-shades-of-serverless --capabilities CAPABILITY_IAM
```

## Ping
```
curl -s --request POST -H "Content-Type: application/json" --data '{"name": "Foo"}' https://8c1isxrana.execute-api.eu-central-1.amazonaws.com/Prod/ping/foo
```

## Transform
```
curl -s -H "Content-Type: application/octet-stream" --data-binary @input.xml  https://8c1isxrana.execute-api.eu-central-1.amazonaws.com/Prod/transform/a
```

## Create bucket for S3 tests
```
aws s3 mb s3://transform-bucket --region eu-central-1
```

## SQS tests Windows
```
set /p INPUT=<input.xml
aws sqs --queue-url https://sqs.eu-central-1.amazonaws.com/047533455857/50shades-input --message-body %INPUT%

aws sqs receive-message --queue-url https://sqs.eu-central-1.amazonaws.com/047533455857/50shades-result
```

## SQS tests Bash
```
aws sqs send-message --queue-url https://sqs.eu-central-1.amazonaws.com/047533455857/50shades-input --message-body $(cat input.xml) 
```