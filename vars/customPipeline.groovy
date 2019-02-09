import com.github.poulad.jenkins.ShellExecutor

def call(String args) {
   //noinspection GroovyAssignabilityCheck
   pipeline {
      agent any
      stages {
         stage('Shell Command') {
            steps {
               script {
                  ShellExecutor.exec(this.steps, "LABEL: ${args}", args)
               }
            }
         }
      }
   }
}
