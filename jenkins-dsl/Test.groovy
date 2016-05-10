freeStyleJob('Test_Job') {   
    steps{
        shell('''echo "Hello world !"
        ''')
    }
}