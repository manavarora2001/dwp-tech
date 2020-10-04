pipeline {

    agent any
    environment {
        NEW_VERSION = '1.5.0'
    }
    parameters {
        choice(name: 'VERSION', choices :['1.1.0','1.2.0','1.3.0'], description: '')
        booleanParam(name: 'executeTests', defaultValue: true , description: '')
    }
    
    stages {
    
       stage ("build") {   
           
          
         steps {
                withMaven(maven:'Maven.3.6'){
                    sh 'mvn clean compile'
                }
         }         
       }
       
      stage ("test") {         
         steps {
            echo 'testing the application ....'
            withMaven(maven:'Maven.3.6'){
               sh 'mvn test'
             }         
 
         }         
       }


       stage ("deploy") {
         steps {
            echo 'deploying the application ....'
         }         
       }
    }
    
    post {
        always {
            //executed always if the build fails or succeeds
            echo 'in always section ....'
        }
        success {
            //execute this if build is success     
            echo 'Build is SUCCESS section ....'
        }
       failure {
            //execute this if build is success
            echo 'Build is FAILURE section ....'
        }

        
    }
 }
