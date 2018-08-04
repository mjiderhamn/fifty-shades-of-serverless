## Archetype
```
mvn archetype:generate -DgroupId=se.jiderhamn.fifty-shades-of-serverless -DartifactId=serverless-aws-jersey -Dversion=1.0.0-SNAPSHOT -DarchetypeGroupId=com.amazonaws.serverless.archetypes -DarchetypeArtifactId=aws-serverless-jersey-archetype -DarchetypeVersion=1.1.3
```

## Deploy

```
aws configure
aws s3 mb s3://fifty-shades --region eu-central-1
sam package --template-file template.yaml --output-template-file serverless-output.yaml --s3-bucket fifty-shades
sam deploy --template-file serverless-output.yaml --stack-name fifty-shades-of-serverless --capabilities CAPABILITY_IAM

# Should be same as 
# aws cloudformation package --template-file template.yaml --output-template-file serverless-output.yaml --s3-bucket fifty-shades 
# aws cloudformation deploy --template-file serverless-output.yaml --stack-name fifty-shades-of-serverless --capabilities CAPABILITY_IAM

```

## Test
```
curl --request POST -H "Content-Type: application/json" --data '{"name": "Foo"}'  https://bqv3qsahj0.execute-api.eu-central-1.amazonaws.com/Prod/ping/foo
```

## Invoke
```
curl -H "Content-Type: application/octet-stream" --data-binary @input.xml https://tz8k2kltb1.execute-api.eu-central-1.amazonaws.com/Prod/transform
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