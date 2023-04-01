#!/bin/zsh

declare -a images=(
   "shevelyanchik/api-gateway"
   "shevelyanchik/auth-service"
   "shevelyanchik/config-service"
   "shevelyanchik/email-service"
   "shevelyanchik/order-service"
   "shevelyanchik/user-service"
)

  commitNumber=$(git rev-list HEAD --count)
  commitHash=$(git rev-parse --short HEAD)
  branchName=$(git rev-parse --abbrev-ref HEAD)
  appVersion="${commitNumber}-${commitHash}-${branchName}"

  echo dckr_pat_9KXVvvHCFtYyOdNxc4uy518pQHA | /usr/local/bin/docker login -u shevelyanchik --password-stdin

for image in ${images[@]};
do
  /usr/local/bin/docker push "$image:$appVersion"
done

  /usr/local/bin/docker logout