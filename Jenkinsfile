pipeline {
  
  agent any

  environment {
    MAVEN_BUILD_PROPERTIES=''
  }

  triggers {
    snapshotDependencies()
    parameterizedCron '15 23 * * * % DEPLOY_ARTIFACTS=true;RUN_SONAR=true;CRON=true'
  }

  tools {
    maven 'Maven 3.5.x' 
  }

  stages {
    stage('Build') {
      options {
        timeout(time: 5, unit: 'MINUTES') 
      }    
      steps {
        sh 'mvn clean install -Ptest-coverage,indoqa-release ${MAVEN_BUILD_PROPERTIES}'
      }
    }

    stage('SonarQube analysis') { 
      when {
        environment name: 'RUN_SONAR', value: 'true'
      }

      steps {
        withSonarQubeEnv('sonar') { 
          sh 'mvn -Ptest-coverage,indoqa-release sonar:sonar -Dsonar.host.url=$SONAR_HOST_URL -Dsonar.login=$SONAR_AUTH_TOKEN -Dsonar.password= '
        }
      }
    }

    stage('Deploy to nexus') {
      when {
        environment name: 'DEPLOY_ARTIFACTS', value: 'true'
      }
      
      steps {
        sh 'mvn deploy ${MAVEN_BUILD_PROPERTIES}'
      }
    }
  }

  post {
    changed {
      echo "Changed to ${currentBuild.result}"
      script {
        if(currentBuild.resultIsBetterOrEqualTo('SUCCESS')) {
          slackSend channel: '#ci_oss', color: '#008000', tokenCredentialId: 'Slack_IntegrationToken'
		message: '${env.JOB_NAME} has recovered at ${env.BUILD_NUMBER} status: ${currentBuild.result} (<a href="${env.BUILD_URL}">Open</a>)'
        }
        if(currentBuild.resultIsWorseOrEqualTo('FAILURE')) {
          slackSend channel: '#ci_oss', color: '#800000', tokenCredentialId: 'Slack_IntegrationToken'
		message: '${env.JOB_NAME} has failed at ${env.BUILD_NUMBER} status: ${currentBuild.result} (<a href="${env.BUILD_URL}">Open</a>)'
        }
      }
    }
  }
}

