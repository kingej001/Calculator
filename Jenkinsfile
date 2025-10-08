pipeline {
    agent {
    label "pipeline"
    }
    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/kingej001/Calculator.git', branch: 'master'
            }
        }
        stage('version check') {
            steps {
                bat "docker version"
            }
        }
        stage('gradle build') {
                     steps {
                         bat "./gradlew clean"
                         bat "./gradlew build"
                     }
                }
        stage("Code Coverage") {
              steps {
                  bat "./gradlew jacocoTestReport"
                  publishHTML(target: [
                                      allowMissing: false,
                                      alwaysLinkToLastBuild: false,
                                      keepAll: true,
                                      reportDir: 'build/reports/jacoco/test/html',
                                      reportFiles: 'index.html',
                                      reportName: 'JaCoCo Report'
                                  ])
                  bat "./gradlew jacocoTestCoverageVerification"
              }
        }
        stage("Static code analysis") {
               steps {
                   bat "./gradlew checkstyleMain"
                   publishHTML(target: [
                                      allowMissing: false,
                                      alwaysLinkToLastBuild: false,
                                      keepAll: true,
                                      reportDir: 'build/reports/checkstyle',
                                      reportFiles: 'main.html',
                                      reportName: 'Checkstyle Report'
                                  ])
               }
        }
        stage('docker build') {
                 steps {
                     bat "docker build -t jebbaemmanuel1/calculator ."
                 }
        }

        stage('docker push') {
                 steps {
                     bat "docker push jebbaemmanuel1/calculator"
                 }
        }

        stage('docker run') {
                 steps {
                     bat "docker run -d -p 9090:9090 --name calculator jebbaemmanuel1/calculator"
                 }
        }

        stage('Acceptance Test') {
                 steps {
                     bat "./gradlew acceptanceTest"
                 }
        }


    }
    /** post {
           always {
                mail to: 'ahmodolaitan03@gmail.com',
                    subject: "Completed Pipeline for: ${currentBuild.fullDisplayName}",
                    body: "Your build completed, please check: ${env.BUILD_URL}"
                slackSend channel: '#test', color: 'green', message: "The pipeline ${currentBuild.fullDisplayName} result."

        }  }**/
}
