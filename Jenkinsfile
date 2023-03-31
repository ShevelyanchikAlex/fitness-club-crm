pipeline {
        agent any

        stages {
            stage('Docker login') {
                steps {
                    echo 'docker logging...'
                    sh '/usr/local/bin/docker ps && echo $SHELL'
                }
            }

//             stage('Docker build') {
//                 steps {
//                     echo 'docker building...'
//                     sh './gradlew buildDockerImage'
//                 }
//             }
//
//             stage('Docker push') {
//                 steps {
//                     echo 'docker pushing...'
// //                     sh './gradlew loginDocker pushDockerImage'
//                     sh 'whereis docker && docker ps'
//                 }
//             }
//
//             stage('Kubernetes deploy') {
//                 steps {
//                     echo 'kubernetes deploying...'
// //                     sh './gradlew applyDeployment'
//                     sh 'whereis docker && docker ps'
//                 }
//             }
//
//             stage('Docker logout') {
//                 steps {
//                     echo 'docker logout...'
//                     sh './gradlew logoutDocker'
//                 }
//             }
        }
    }