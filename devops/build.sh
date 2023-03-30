#!/bin/zsh

declare -a services=(
   "api-gateway"
   "auth-service"
   "config-server"
   "email-service"
   "order-service"
   "user-service"
)

for service in ${services[@]}; do
  ./gradlew $service:build
done