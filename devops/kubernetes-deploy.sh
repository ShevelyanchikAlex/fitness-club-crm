#!/bin/zsh

declare -a images=(
   "shevelyanchik\/api-gateway"
   "shevelyanchik\/auth-service"
   "shevelyanchik\/config-service"
   "shevelyanchik\/email-service"
   "shevelyanchik\/order-service"
   "shevelyanchik\/user-service"
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

  cd k8s

for deployment in ${deploymentFiles[@]};
do
  index=$(( index + 1 ))
#  This regex uses for tag version replacing
  sed -i '' "s/${images[$index]}:[0-9]\{1,\}\-[a-zA-Z0-9_]\{1,\}\-[a-zA-Z1-9_]\{1,\}/${images[$index]}:$appVersion/g" $deployment
  /usr/local/bin/kubectl apply -f $deployment
done

git add .
git commit -m "Images update: $appVersion"
git push