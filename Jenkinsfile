pipeline {
 
  agent any
  
  stages {
    stage("build") {
      steps {
        sh 'mvn clean install -DskipTests'
      }
    }
    
    stage ("test") {
      steps {
       sh 'mvn clean install'
      }
    }
    
   stage("Done"){
    steps {
      echo "Done"
    }
  }
}
