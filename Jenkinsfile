pipeline {
        agent any

//         environment {
//             DOCKERHUB_CREDENTIALS = credentials('dockerhub')
//           }

        stages {
            stage('Docker build') {
                steps {
                    echo 'docker building...'
//                     sh 'docker build -t shevelyanchik/config-service:latest config-server'
                }
            }

//             stage('Docker login') {
//                 steps {
//                     sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
//                 }
//             }

            stage('Docker push') {
                steps {
                    echo 'docker pushing...'
//                     sh 'docker push shevelyanchik/config-service:latest'
                }
            }

            stage('Kubernetes deploy') {
                steps {
                    echo 'kubernetes deploying...'
                }
            }
        }

//         post {
//            always {
//              sh 'docker logout'
//            }
//         }
    }