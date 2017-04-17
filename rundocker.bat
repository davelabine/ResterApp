# Set environment variables for service credentials
source ./secret/cred-config.bat 
# Run the docker image with the appropriate environment variables
docker run --name resterapp -d -e AWS_ACCESS_KEY_ID -e AWS_SECRET_ACCESS_KEY -e AWS_DEFAULT_REGION -e DB_ENV_UNAME -e DB_ENV_PW -p 8080:8080 davelabine/resterapp