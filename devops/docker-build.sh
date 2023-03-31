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

  commitNumber=$(git rev-list HEAD --count)
  commitHash=$(git rev-parse --short HEAD)
  branchName=$(git rev-parse --abbrev-ref HEAD)
  appVersion="${commitNumber}-${commitHash}-${branchName}"

  index=0

for image in ${images[@]};
do
  index=$(( index + 1 ))
  /usr/local/bin/docker build -t "$image:$appVersion" "${services[$index]}"
done