pipeline {
        agent any

        environment {
           DOCKERHUB_CREDENTIALS = credentials('dockerhub')
        }



        stages {
            stage('Docker login') {
                steps {
                    sh 'echo $DOCKERHUB_CREDENTIALS_PSW | docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                }
            }
            stage('Docker build and push') {
                steps {
                    echo 'docker building and pushing...'
                    sh './gradlew build buildAndPushDockerImage'
                }
            }

            stage('Kubernetes deploy') {
                steps {
                    echo 'kubernetes deploying...'
                }
            }
        }

        post {
           always {
             sh 'docker logout'
           }
        }
    }