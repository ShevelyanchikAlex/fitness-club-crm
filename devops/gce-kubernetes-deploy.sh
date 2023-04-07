#!/bin/bash

declare -a images=(
   "europe-central2-docker.pkg.dev\/skilful-display-384007\/fitness-club-repo\/api-gateway"
   "europe-central2-docker.pkg.dev\/skilful-display-384007\/fitness-club-repo\/auth-service"
   "europe-central2-docker.pkg.dev\/skilful-display-384007\/fitness-club-repo\/config-service"
   "europe-central2-docker.pkg.dev\/skilful-display-384007\/fitness-club-repo\/email-service"
   "europe-central2-docker.pkg.dev\/skilful-display-384007\/fitness-club-repo\/order-service"
   "europe-central2-docker.pkg.dev\/skilful-display-384007\/fitness-club-repo\/user-service"
)

declare -a deploymentFiles=(
   "api-gateway-deployment.yaml"
   "auth-deployment.yaml"
   "config-deployment.yaml"
   "email-deployment.yaml"
   "order-deployment.yaml"
   "user-deployment.yaml"
)

  commitNumber=$(git rev-list HEAD --count)
  commitHash=$(git rev-parse --short HEAD)
  branchName=$(git rev-parse --abbrev-ref HEAD)
  appVersion="${commitNumber}-${commitHash}-${branchName}"

  index=0

  cd k8s-cloud

for deployment in ${deploymentFiles[@]};
do
#  This regex uses for tag version replacing
  sed -i "s/${images[$index]}:[0-9]\{1,\}\-[a-zA-Z0-9_]\{1,\}\-[a-zA-Z1-9_]\{1,\}/${images[$index]}:$appVersion/g" $deployment
  echo ${images[$index]}:$appVersion
  index=$(( index + 1 ))
done

git add .
git commit -m "Images update: $appVersion"