pipeline {

    agent any

    parameters {
        booleanParam(name: 'ALLURE',defaultValue: false,description: 'Generation du rapport Allure')
        booleanParam(name: 'SUREFIRE',defaultValue: false,description: 'Generation du rapport Surefire')
        choice(name: 'TAG',choices: ['ALL','@loginValid','@loginInvalid','@loginMyActions','@loginQuickLaunch'],description: 'Choisir le tag à lancer')
    }

    stages {

        stage('global stage') {

            agent {
                docker {
                    image 'maven:3.9.9-eclipse-temurin-17'
                    args '-u root --entrypoint='
                }
            }

            stages {

                stage('installer dependencies') {
                    steps {
                        sh 'mvn dependency:resolve'
                    }
                }

                stage('clean reports') {
                    steps {
                        sh '''
                            echo "Suppression des anciens rapports..."

                            rm -rf allure-results
                            rm -rf cypress/reports

                            mkdir -p allure-results
                            mkdir -p cypress/reports

                            echo "Dossiers nettoyes avec succes"
                        '''
                    }
                }

                stage('run Selenium tests') {
                    steps {
                        script {

                            if (params.TAG == 'ALL') {

                                sh 'mvn test'

                            } else {

                                sh "mvn test -Dcucumber.filter.tags='${params.TAG}'"

                            }
                        }
                    }
                }

                stage('generate Surefire report') {

                    when {
                        expression {
                            return params.SUREFIRE
                        }
                    }

                    steps {

                        sh '''
                            echo "=== SUREFIRE REPORT ==="

                            ls -la target/reports

                            echo "=== SUREFIRE HTML ==="

                            ls -la target/reports/surefire.html
                        '''

                        stash(
                            name: 'surefire-report',
                            includes: 'target/reports/**/*'
                        )
                    }
                }

                stage('check allure results') {

                    when {
                        expression {
                            return params.ALLURE
                        }
                    }

                    steps {

                        sh '''
                            echo "=== ALLURE RESULTS ==="
                            ls -la allure-results

                            echo "=== RESULT JSON ==="
                            cat allure-results/*-result.json

                            echo "=== END ==="
                        '''

                        stash name: 'allure-results', includes: 'allure-results/**/*'
                    }
                }
            }
        }
    }

    post {

        always {

            script {

                if (params.ALLURE) {

                    unstash 'allure-results'

                    archiveArtifacts(
                        artifacts: 'allure-results/**/*',
                        allowEmptyArchive: true
                    )

                    allure(
                        includeProperties: false,
                        jdk: '',
                        results: [[path: 'allure-results/']]
                    )
                }

                if (params.SUREFIRE) {

                    echo "=== PUBLICATION DU RAPPORT SUREFIRE ==="

                    unstash 'surefire-report'

                    archiveArtifacts(
                        artifacts: 'target/reports/**/*',
                        allowEmptyArchive: true
                    )

                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'target/reports',
                        reportFiles: 'surefire.html',
                        reportName: 'Surefire Report'
                    ])
                }
            }
        }
    }
}