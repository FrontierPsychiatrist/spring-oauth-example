#!/bin/sh
# Accquire token from the OAuth server with our client credentials
TOKEN=`curl -s -u curl-client:client-secret -X POST localhost:8081/oauth/token\?grant_type=client_credentials | egrep -o '[a-f0-9-]{20,}'`
echo "Got token $TOKEN"
curl localhost:8080/todos -H "Authorization: Bearer 65fde387-5175-48ec-86b2-40b5b4758e3e"