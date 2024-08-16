#!/bin/bash

# Set the host
HOST="http://localhost:8080"

# Function to extract token from header and remove \r
extract_token() {
    echo "$1" | grep -i "Authorization:" | sed 's/Authorization: Bearer //' | tr -d '\r'
}

# Login as organizer
organizer_response=$(curl -s -i -X POST "$HOST/api/users/login" \
     -H "Content-Type: application/json" \
     -d '{"username": "organizer", "password": "123456"}')

organizer_token=$(extract_token "$organizer_response")

# Login as user
user_response=$(curl -s -i -X POST "$HOST/api/users/login" \
     -H "Content-Type: application/json" \
     -d '{"username": "testuser", "password": "123456"}')

user_token=$(extract_token "$user_response")

# Update http-client.env.json
ENV_FILE="http-client.env.json"

# Create the file if it doesn't exist
if [ ! -f "$ENV_FILE" ]; then
    echo '{"organizer":{"host":"http://localhost:8080","authToken":"tokenplaceholder"},"user":{"host":"http://localhost:8080","authToken":"tokenplaceholder"}}' > "$ENV_FILE"
fi

jq --arg organizer_token "$organizer_token" --arg user_token "$user_token" \
   '.organizer.authToken = $organizer_token | .user.authToken = $user_token' \
   "$ENV_FILE" > temp.json && mv temp.json "$ENV_FILE"

echo "Tokens updated successfully!"
