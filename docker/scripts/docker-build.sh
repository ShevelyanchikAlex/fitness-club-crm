#!/bin/zsh

declare -a images=(
   "fitness-club/api-gateway"
   "fitness-club/auth-service"
   "fitness-club/config-service"
   "fitness-club/email-service"
   "fitness-club/order-service"
   "fitness-club/user-service"
   "fitness-club/news-service"
)

declare -a services=(
   "api-gateway"
   "auth-service"
   "config-server"
   "email-service"
   "order-service"
   "user-service"
   "news-service"
)

  cd "../../"

  index=0
  tag="prod-1.2"

for image in ${images[@]};
do
  index=$(( index + 1 ))
  /usr/local/bin/docker build -t "$image:$tag" "${services[$index]}"
done