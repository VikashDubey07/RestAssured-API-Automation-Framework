pipeline {
    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Build') {
            steps {
                git 'https://github.com/jglick/simple-maven-project-with-tests.git'
                bat "mvn -Dmaven.test.failure.ignore=true clean package"
            }
            post {
                success {
                    junit '**/target/surefire-reports/TEST-*.xml'
                    archiveArtifacts 'target/*.jar'
                }
            }
        }

        stage("Deploy to QA") {
            steps {
                echo("deploy to qa done")
            }
        }

        stage('Regression API Automation Tests') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/VikashDubey07/RestAssured-API-Automation-Framework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/TestRunner/goRestApi.xml"
                }
            }
        }

        stage('Publish Regression Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }

        stage('Publish Regression ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML API Regression ChainTest Report',
                    reportTitles: ''
                ])
            }
        }

        stage("Deploy to Stage") {
            steps {
                echo("deploy to Stage")
            }
        }

        stage('Sanity API Automation Test') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    git 'https://github.com/VikashDubey07/RestAssured-API-Automation-Framework.git'
                    bat "mvn clean test -Dsurefire.suiteXmlFiles=src/test/resources/TestRunner/goRestApi.xml"
                }
            }
        }
        
        
        
        stage('Publish Sanity Allure Reports') {
            steps {
                script {
                    allure([
                        includeProperties: false,
                        jdk: '',
                        properties: [],
                        reportBuildPolicy: 'ALWAYS',
                        results: [[path: 'allure-results']]
                    ])
                }
            }
        }
        
        
        stage('Publish Sanity ChainTest Report') {
            steps {
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: false,
                    keepAll: true,
                    reportDir: 'target/chaintest',
                    reportFiles: 'Index.html',
                    reportName: 'HTML API Regression ChainTest Report',
                    reportTitles: ''
                ])
            }
        }
        

        stage("Deploy to prod") {
            steps {
                echo("deploy to prod")
            }
        }

    }
}