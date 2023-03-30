#!/bin/zsh

declare -a deploymentFiles=(
   "api-gateway-deployment.yaml"
   "auth-deployment.yaml"
   "config-deployment.yaml"
   "email-deployment.yaml"
   "order-deployment.yaml"
   "user-deployment.yaml"
)

  cd k8s

for deployment in ${deploymentFiles[@]};
do
  /usr/local/bin/kubectl apply -f $deployment
done