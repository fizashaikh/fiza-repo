// Comments here
folder('Test_Area') {
    description('Playground')
    primaryView("Pipeline")
}
freeStyleJob('Test_Area/Downstream_Job') {   
    steps{
        shell('''echo "Hello world Again!"
        ''')
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

freeStyleJob('Test_Area/Test_Job') {   
    multiscm {
        git {
            remote {
                url('https://github.com/fizashaikh/fiza-repo.git')
                credentials('git-user')
            }
            branch('develop')
        }
        git {
            remote {
                url('https://github.com/fizashaikh/test-repo.git')
                credentials('git-user')
            }
            branch('develop')
        }
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

buildPipelineView('Test_Area/Pipeline') {
    filterBuildQueue()
    filterExecutors()
    title('My Pipeline')
    displayedBuilds(5)
    selectedJob('Test_Area/Test_Job')
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(60)
}
