pipeline {
        agent any

        stages {
            stage('Docker login') {
                steps {
                    echo 'docker logging...'
                    sh './gradlew loginDocker'
                }
            }

            stage('Docker build') {
                steps {
                    echo 'docker building...'
                    sh './gradlew buildDockerImage'
                }
            }

            stage('Docker push') {
                steps {
                    echo 'docker pushing...'
//                     sh './gradlew pushDockerImage'
                }
            }

            stage('Kubernetes deploy') {
                steps {
                    echo 'kubernetes deploying...'
                    sh 'cd k8s | kubectl apply -f api-gateway-deployment.yaml'
                }
            }

            stage('Docker logout') {
                steps {
                    echo 'docker logout...'
                    sh './gradlew logoutDocker'
                }
            }
        }
    }