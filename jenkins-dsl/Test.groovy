folder('Test Area') {
    description('Playground')
}
freeStyleJob('Downstream_Job') {   
    steps{
        shell('''echo "Hello world Again!"
        ''')
    }
    configure { project ->
		def slackXmlNode = project / 'publishers' / 'jenkins.plugins.slack.SlackNotifier'
                slackXmlNode.appendNode('notifyBuildStart', 'true')
    }
}

freeStyleJob('Test_Job') {   
    scm {
        git {
            remote {
                url('https://github.com/fizashaikh/fiza-repo.git')
                credentials('git-user')
            }
            branch('develop')
        }
    }
    triggers {
        scm('* * * * *')
    }
    steps{
        shell('''echo "Hello world !"
        ''')
    }
    publishers {
        downstreamParameterized {
            trigger('Downstream_Job') {
                condition('SUCCESS')
                triggerWithNoParameters()
            }
        }
    }
    configure { project ->
		def slackXmlNode = project / 'publishers' / 'jenkins.plugins.slack.SlackNotifier'
                slackXmlNode.appendNode('notifySuccess', 'true')
                slackXmlNode.appendNode('notifyAborted', 'true')
                slackXmlNode.appendNode('notifyNotBuilt', 'true')
                slackXmlNode.appendNode('notifyUnstable', 'true')
                slackXmlNode.appendNode('notifyFailure', 'true')
                slackXmlNode.appendNode('notifyBackToNormal', 'true')
                slackXmlNode.appendNode('notifyRepeatedFailure', 'true')
                slackXmlNode.appendNode('commitInfoChoice', 'AUTHORS_AND_TITLES')
            }
}

buildPipelineView('My Test View') {
    filterBuildQueue()
    filterExecutors()
    title('My Pipeline')
    displayedBuilds(5)
    selectedJob('Test_Job')
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(60)
}
