#!/bin/bash

declare -a images=(
   "api-gateway"
   "auth-service"
   "config-service"
   "email-service"
   "order-service"
   "user-service"
)
  commitNumber=$(git rev-list HEAD --count)
  commitHash=$(git rev-parse --short HEAD)
  branchName=$(git rev-parse --abbrev-ref HEAD)
  appVersion="${commitNumber}-${commitHash}-${branchName}"
  registryHost="europe-central2-docker.pkg.dev/skilful-display-384007/fitness-club-repo"

for image in ${images[@]};
do
  docker image tag "$image:$appVersion" "$registryHost/$image:$appVersion"
  docker push "$registryHost/$image:$appVersion"
done