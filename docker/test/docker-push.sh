#!/bin/zsh

declare -a images=(
   "shevelyanchik/api-gateway"
   "shevelyanchik/auth-service"
   "shevelyanchik/config-service"
   "shevelyanchik/email-service"
   "shevelyanchik/order-service"
   "shevelyanchik/user-service"
)

  echo dckr_pat_9KXVvvHCFtYyOdNxc4uy518pQHA | /usr/local/bin/docker login -u shevelyanchik --password-stdin

for image in ${images[@]};
do
  /usr/local/bin/docker push "$image:test"
done

  /usr/local/bin/docker logout