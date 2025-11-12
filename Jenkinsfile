pipeline {
    agent { label 'pipeline' }

    stages {
        stage('Checkout') {
            steps {
                git url: 'https://github.com/kingej001/Calculator.git', branch: 'master'
            }
        }

        stage('Version Check') {
            steps {
                bat 'docker version'
                bat 'wsl -d Ubuntu ansible --version'
            }
        }

        stage('Gradle Build') {
            steps {
                bat 'gradlew.bat clean'
                bat 'gradlew.bat build'
            }
        }

        stage('Code Coverage') {
            steps {
                bat 'gradlew.bat jacocoTestReport'
                script {
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: 'build/reports/jacoco/test/html',
                        reportFiles: 'index.html',
                        reportName: 'JaCoCo Report'
                    ])
                }
                bat 'gradlew.bat jacocoTestCoverageVerification'
            }
        }

        stage('Static Code Analysis') {
            steps {
                bat 'gradlew.bat checkstyleMain'
                script {
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
        }

        stage('Docker Build') {
            steps {
                bat 'docker build -t jebbaemmanuel1/calculator .'
            }
        }

        stage('Docker Push') {
            steps {
                bat 'docker push jebbaemmanuel1/calculator'
            }
        }

        stage('Docker Run') {
            steps {
                bat 'docker run -d -p 9090:9090 --name calculator jebbaemmanuel1/calculator'
            }
        }

        stage('Acceptance Test') {
            steps {
                bat 'gradlew.bat acceptanceTest'
            }
        }

        stage('Deploy') {
            steps {
                bat 'wsl -d Ubuntu ansible-playbook -i /home/emma/ansible/hosts calculator.yaml'
            }
        }
    }
}
