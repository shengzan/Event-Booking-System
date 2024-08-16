#!/bin/bash

# Set the host
HOST="http://localhost:8080"

# Function to extract token from response
extract_token() {
    echo "$1" | grep -o '"token":"[^"]*' | sed 's/"token":"//'
}

# Login as organizer
organizer_response=$(curl -s -X POST "$HOST/api/users/login" \
     -H "Content-Type: application/json" \
     -d '{"username": "organizer", "password": "123456"}')

organizer_token=$(extract_token "$organizer_response")

# Login as user
user_response=$(curl -s -X POST "$HOST/api/users/login" \
     -H "Content-Type: application/json" \
     -d '{"username": "jason", "password": "123456"}')

user_token=$(extract_token "$user_response")

# Update http-client.env.json
ENV_FILE="/Users/zanzan/Documents/GitHub/Event-Booking-System/src/test/resources/http/http-client.env.json"

# Create the file if it doesn't exist
if [ ! -f "$ENV_FILE" ]; then
    echo '{"dev":{"host":"http://localhost:8080","authToken":"","userAuthToken":""}}' > "$ENV_FILE"
fi

jq --arg organizer_token "$organizer_token" --arg user_token "$user_token" \
   '.dev.authToken = $organizer_token | .dev.userAuthToken = $user_token' \
   "$ENV_FILE" > temp.json && mv temp.json "$ENV_FILE"

echo "Tokens updated successfully!"
