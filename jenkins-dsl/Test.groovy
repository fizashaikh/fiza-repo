freeStyleJob('Test_Job') {   
    steps{
        shell('''echo "Hello world !"
        ''')
    }
    publishers {
        slackNotifications {
            projectChannel('#slack-notifications')
            notifySuccess()
            notifyAborted()
            notifyFailure()
            notifyNotBuilt()
            notifyUnstable()
            notifyBackToNormal()
            notifyRepeatedFailure()
            showCommitList()
        }
    }
}
