pipeline {
 
  agent any
  
  stages {
    stage("build") {
      steps {
        mvn clean install -DskipTests
      }
    }
    
    stage ("test") {
      steps {
        mvn clean install
      }
    }
    
    stage("Done")
      echo "Done"
  }
}
