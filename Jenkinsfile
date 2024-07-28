pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from GitHub
                git 'https://github.com/Chit-Senghang/minio-sonar.git'
            }
        }

        stage('Build') {
            steps {
                // Run your build commands here
                echo 'Building...'
            }
        }

        stage('Test') {
            steps {
                // Run your test commands here
                echo 'Testing...'
            }
        }

        stage('Deploy') {
            steps {
                // Run your deploy commands here
                echo 'Deploying...'
            }
        }
    }
}
