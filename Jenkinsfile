pipeline {
    agent any

    environment {
        JAVA_HOME = "${tool 'JAVA_17'}"
        PATH = "${tool 'JAVA_17'}/bin:${env.PATH}"
        SCANNER_HOME = tool 'SONAR_SCANNER'
        SONARQUBE_LOCAL = 'SONAR_LOCAL'
        SONARQUBE_QG = 'SONAR_LOCAL_QG'
        SONARQUBE_URL = 'http://localhost:9000/'
        SONARQUBE_TOKEN = 'c163ea73dffd8fb0214151b4b59770fe234885d2'
        TOMCAT_LOGIN = 'TOMCAT_LOGIN'
        TOMCAT_URL = 'http://localhost:8001/'
    }

    stages {
        stage('Build Backend') {
            steps {
                bat 'java -version'
                bat 'javac -version'
                bat 'mvn clean package -DskipTests=true'
            }
        }

        stage('Unit Tests') {
            steps {
                bat 'mvn test'
            }
        }

        stage('Sonar Analysis') {
            steps {
                    withSonarQubeEnv(SONARQUBE_LOCAL) {

                    }
            }
        }

        stage('Quality Gate') {
            steps {
                sleep(5)
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}

