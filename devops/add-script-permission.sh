#!/bin/bash

declare -a scripts=(
   "devops/gce-build.sh"
   "devops/gce-docker-build.sh"
   "devops/gce-docker-push.sh"
   "devops/gce-kubernetes-deploy.sh"
)

for script in ${scripts[@]};
do
  chmod +x $script
done