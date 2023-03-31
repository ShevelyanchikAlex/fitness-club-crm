pipeline {
        agent any

        stages {
            stage('Docker login') {
                steps {
                    echo 'docker login...'
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
                    sh './gradlew pushDockerImage'
                }
            }

            stage('Kubernetes deploy') {
                steps {
                    echo 'kubernetes deploying...'
                }
            }

            stage('Docker logout') {
                steps {
                    echo 'docker login...'
                    sh './gradlew logoutDocker'
                }
            }
        }
    }