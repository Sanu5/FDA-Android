pipeline {
  agent any

  stages {
    stage('Checkout') {
      steps {
        git url: 'https://github.com/Sanu5/FDA-Android.git', branch: 'main'
      }
    }

    stage('Build APK') {
      steps {
        sh './gradlew clean assembleDebug'
      }
    }

    stage('Run Tests') {
      steps {
        sh './gradlew test'
      }
    }

    stage('Archive APK') {
      steps {
        archiveArtifacts artifacts: '**/app/build/outputs/**/*.apk', fingerprint: true
      }
    }
  }

  post {
    success {
      echo 'Build successful! 📦'
    }
    failure {
      echo 'Build failed.'
    }
  }
}
