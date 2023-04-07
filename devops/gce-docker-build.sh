#!/bin/bash

declare -a images=(
   "api-gateway"
   "auth-service"
   "config-service"
   "email-service"
   "order-service"
   "user-service"
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
  docker build -t "$image:$appVersion" "${services[$index]}"
  index=$(( index + 1 ))
done