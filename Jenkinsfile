pipeline {
    agent { dockerfile true }
    stages {
        stage('build') {
            steps {
                sh 'echo "testuje eho"'
                sh 'docker build -t pinger-img .'
            }
        }
    }
}