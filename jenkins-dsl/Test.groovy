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
