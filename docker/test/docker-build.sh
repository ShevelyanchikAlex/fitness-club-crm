#!/bin/zsh

declare -a images=(
   "shevelyanchik/api-gateway"
   "shevelyanchik/auth-service"
   "shevelyanchik/config-service"
   "shevelyanchik/email-service"
   "shevelyanchik/order-service"
   "shevelyanchik/user-service"
)

declare -a services=(
   "api-gateway"
   "auth-service"
   "config-server"
   "email-service"
   "order-service"
   "user-service"
)

  index=0

for image in ${images[@]};
do
  index=$(( index + 1 ))
  /usr/local/bin/docker build -t "$image:test" "${services[$index]}"
done