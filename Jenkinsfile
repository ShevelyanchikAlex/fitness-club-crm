pipeline {
        agent any

        stages {
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
    }