node('maven') {
    stage('build') {
        echo 'building app :)'
        openshiftBuild(buildConfig: 'php', showBuildLogs: 'true')
    }
    stage('verify') {
        echo 'dummy verification....'
    }
    stage('deploy') {
        input 'Manual Approval'
        openshiftDeploy(deploymentConfig: 'php')
    }
    stage('promoting to QA') {
       echo 'fake stage...'
       sleep 5
    }
}
