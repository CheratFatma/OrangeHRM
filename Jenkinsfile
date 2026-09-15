pipeline {
    agent any

    parameters {
        booleanParam(name: 'ALLURE',defaultValue: false,description: 'Generation du rapport Allure')
        booleanParam(name: 'SUREFIRE',defaultValue: false,description: 'Generation du rapport Surefire')
        choice(name: 'TAG',choices: ['ALL','@loginValid','@loginInvalid','@loginMyActions','@loginQuickLaunch'],description: 'Choisir le tag à lancer')
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Tests Selenium') {
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
        }

        success {
            echo 'Pipeline terminée avec succès'
        }

        failure {
            echo 'Les tests Selenium ont échoué'
        }
    }
}


