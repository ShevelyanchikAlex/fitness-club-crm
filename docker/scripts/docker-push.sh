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

  echo dckr_pat_ukCP_9kb_TSMyyYCRHusGXV5Ovs | /usr/local/bin/docker login -u shevelyanchik --password-stdin

  tag="prod-1.2"

for image in ${images[@]};
do
  /usr/local/bin/docker push "$image:$tag"
done

  /usr/local/bin/docker logout