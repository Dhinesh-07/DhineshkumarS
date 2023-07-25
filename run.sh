#!/bin/bash

if [ -z "$1" ]; then
    echo "Usage: $0 <profile>"
    exit 1
fi

PROFILE=$1

VERSION_FILE="version.txt"

# Function to get the version from the version.txt file or set to 1 if not present
get_version() {
    if [ -f "$VERSION_FILE" ]; then
        VERSION=$(cat "$VERSION_FILE")
    else
        VERSION=1
        echo "$VERSION" > "$VERSION_FILE"
    fi
}

# Function to increment version
increment_version() {
    VERSION=$((VERSION + 1))
    echo "$VERSION" > "$VERSION_FILE"
}

echo "Building the application (if needed)..."
mvn clean install

# Check if the version needs to be incremented
get_version

# Check if the Docker image already exists for this version
IMAGE_TAG="local:v3.2.$VERSION"
IMAGE_ID=$(docker images -q "$IMAGE_TAG" 2> /dev/null)

if [ -z "$IMAGE_ID" ]; then
    echo "Building the Docker image..."
    docker build -t "$IMAGE_TAG" .
fi

# Stop and remove the existing container (if exists)
CONTAINER_NAME="local3.2.$VERSION"
EXISTING_CONTAINER_ID=$(docker ps -aq -f name="$CONTAINER_NAME")

if [ -n "$EXISTING_CONTAINER_ID" ]; then
    docker stop "$EXISTING_CONTAINER_ID" >/dev/null
    docker rm "$EXISTING_CONTAINER_ID" >/dev/null
fi

# Run the Docker container with the specified profile
echo "Running the application with the '$PROFILE' profile..."

##docker run -p 8080:8080 --network="host" -e SPRING_PROFILES_ACTIVE="$PROFILE" --name "$CONTAINER_NAME" "$IMAGE_TAG"
#docker run -p 8080:8080 --network="host" \
#  -e SPRING_PROFILES_ACTIVE="$PROFILE" \
#  -e aws.accessKeyId="$aws.accessKeyId" \
#  -e aws.secretKey="$aws.secretKey" \
#  -e aws.region="$aws.region" \
#  -e aws.Bucket="$aws.Bucket" \
#  --name "$CONTAINER_NAME" "$IMAGE_TAG"


docker run -p 8080:8080 --network="host" \
  -v "$(pwd)/$AWS_PROPERTIES_FILE:/app/application.properties" \
  -e SPRING_PROFILES_ACTIVE="$PROFILE" \
  --name "$CONTAINER_NAME" "$IMAGE_TAG"