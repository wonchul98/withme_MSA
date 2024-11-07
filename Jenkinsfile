pipeline {
    agent any
    
    environment {
        FRONT_ROOT = './frontend'
        BACK_ROOT = './backend'

        frontChanged = true
        backChanged = true
    }

    stages {
        stage('Get branch name') {
            steps {
                script {
                    branchName = env.gitlabBranch
                    echo "branch name : ${branchName}"
    
                    if (branchName == 'frontend/develop') {
                        backChanged = false
                    }
                        
                        
                    if (branchName == 'backend/develop') {
                        frontChanged = false
                    }
                }
            }
        }
        
        stage('Checkout') {
            steps {
                checkout scmGit(
                    branches: [[name: branchName]],
                    userRemoteConfigs: [[credentialsId: 'withme-gitlab',
                    url: 'https://lab.ssafy.com/s11-final/S11P31A507.git']])
            }
        }
        
        stage('Build') {
            steps {
                script {
                    if(backChanged) {
                        dir(BACK_ROOT) {
                            sh 'chmod +x gradlew'
                            sh './gradlew clean build -x test'
                        }
                    }
                    if(frontChanged) {
                        echo 'build frontend code'
                    }
                }
            }
            
            post {
                success { 
                    script {
                        def message = "Build succeeded."
                        if (backChanged) {
                            message += " Backend was changed."
                        }
                        if (frontChanged) {
                            message += " Frontend was changed."
                        }
                        echo message
                    }
                }
                failure {
                    echo 'Failed to Build...'
                }
            }
        }
        
        stage('Generate Docker Image') {
            steps {
                script {
                    if(backChanged) {
                        dir(BACK_ROOT) {
                            script {
                                sh 'docker build -t taegun1011/withme_backend .'
                            }
                        }
                    }
                    if(frontChanged) {
                        dir(FRONT_ROOT) {
                            script {
                                sh 'docker build -t taegun1011/withme_frontend .'
                            }
                        }
                    }
                }
            }
            post {
                success { 
                    script {
                        def message = "New docker image generated :"
                        if (backChanged) {
                            message += " Backend"
                        }
                        if (frontChanged) {
                            message += " Frontend"
                        }
                        echo message
                    }
                }
                failure {
                    echo 'Failed to generate docker image...'
                }
            }
        }
    }
}
