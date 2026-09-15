pipeline {
    agent any

    tools {
        jdk 'JDK8'
        maven 'Maven3'
    }

    parameters {
        booleanParam(name: 'ALLURE',defaultValue: false,description: 'Generation du rapport Allure')
        booleanParam(name: 'SUREFIRE',defaultValue: false,description: 'Generation du rapport Surefire')
        choice(name: 'TAG',choices: ['ALL','@loginValid','@loginInvalid','@loginMyActions','@loginQuickLaunch'],description: 'Choisir le tag à lancer')
    }

    stages {

        stage('Start Selenium') {
            steps {
                sh 'docker compose up -d'
                sh 'docker compose ps'
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Tests Selenium') {
            agent {
                docker {
                    image 'maven:3.8.3-openjdk-17'
                    args "--entrypoint='' --shm-size=2g --network=selenium-network"
                    reuseNode true
                }
            }
            steps {
                script {

                    if (params.TAG == 'ALL') {

                        sh 'mvn test -Dbrowser=chrome'

                    } else {

                        sh "mvn test -Dbrowser=chrome -Dcucumber.filter.tags='${params.TAG}'"

                    }
                }
            }
        }
    }

    post {

        always {

            script {

                // Rapport Surefire uniquement si SUREFIRE est coché
                if (params.SUREFIRE) {

                    echo '=== RAPPORT SUREFIRE ==='

                    junit(
                        testResults: 'target/surefire-reports/*.xml',
                        allowEmptyResults: true
                    )

                    archiveArtifacts(
                        artifacts: 'target/reports/**/*',
                        allowEmptyArchive: true
                    )
                }

                // Rapport Allure uniquement si ALLURE est coché
                if (params.ALLURE) {

                    echo '=== RAPPORT ALLURE ==='

                    allure([
                        includeProperties: false,
                        results: [
                            [path: 'target/allure-results']
                        ]
                    ])

                    archiveArtifacts(
                        artifacts: 'target/allure-results/**/*',
                        allowEmptyArchive: true
                    )
                }
            }
            // arrêter Selenium après les tests
            sh 'docker compose down || true'
        }

        success {
            echo 'Pipeline terminée avec succès'
        }

        failure {
            echo 'Les tests Selenium ont échoué'
        }
    }
}


