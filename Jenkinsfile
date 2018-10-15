pipeline {
    agent any

    environment {
        DOCKER_REPO = "taskagile/vuejs.spring-boot.mysql"
        DOCKER_CREDENTIAL = "dockerhub"
        JENKINS_AT_STAGING = "jenkins@staging.taskagile.com"
    }

    stages {
        stage("Build package") {
            steps {
                echo "Git commit: ${env.GIT_COMMIT}"
                sh "mvn clean package"
            }
        }

        stage("Build Docker image") {
            steps {
                sh "cp target/app-0.0.1-SNAPSHOT.jar docker/app.jar"
                sh "docker build -t ${DOCKER_REPO}:${env.GIT_COMMIT} docker/"
            }
        }

        stage("Push Docker build image") {
            steps {
                withDockerRegistry([ credentialsId: DOCKER_CREDENTIAL, url: '' ]) {
                  sh "docker push ${DOCKER_REPO}:${env.GIT_COMMIT}"
                }
            }
        }

        stage("Deploy to staging") {
            steps {
                sh "ssh ${JENKINS_AT_STAGING} rm -fr /app/start.sh"
                sh "scp ./docker/start.sh ${JENKINS_AT_STAGING}:/app"
                sh "ssh ${JENKINS_AT_STAGING} \"cd /app && ./start.sh ${env.GIT_COMMIT}\""
            }
        }

        stage("Run E2E tests") {
            steps {
                sh "cd ${env.WORKSPACE}/front-end && npm run test:staging-e2e"
            }
        }
    }

    post {
        always {
            emailext (
                subject: "[Jenkins] ${env.JOB_NAME} Build #${env.BUILD_NUMBER} - ${currentBuild.currentResult}",
                recipientProviders: [[$class: 'DevelopersRecipientProvider'], [$class: 'RequesterRecipientProvider']],
                body: "${currentBuild.currentResult}\n\nJob: ${env.JOB_NAME}\nBuild: #${env.BUILD_NUMBER}\nGit commit: ${env.GIT_COMMIT}\nMore detail at: ${env.BUILD_URL}"
            )
            sh "docker rmi -f ${DOCKER_REPO}:${env.GIT_COMMIT}"
        }
    }
}
