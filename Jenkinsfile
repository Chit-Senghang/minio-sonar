node {
  stage('SCM') {
    checkout scm
  }
  stage('Set Permissions') {
    sh 'chmod +x ./gradlew'
  }
  stage('SonarQube Analysis') {
    withSonarQubeEnv() {
      sh './gradlew sonar'
    }
  }
}
