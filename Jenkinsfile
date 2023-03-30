pipeline {
        agent any

        stages {
            stage('Docker build') {
                steps {
                    echo 'docker building...'
                }
            }

            stage('Docker push') {
                steps {
                    echo 'docker pushing...'
                }
            }

            stage('Kubernetes deploy') {
                steps {
                    echo 'kubernetes deploying...'
                }
            }


        }
    }