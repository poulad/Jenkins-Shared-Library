import com.github.poulad.jenkins.ShellExecutor
import com.github.poulad.jenkins.WorkflowSteps

def call(String args) {
   //noinspection GroovyAssignabilityCheck
   pipeline {
      agent any
      stages {
         stage('Shell Command') {
            steps {
               script {
                  echo args
                  ShellExecutor.exec(this.steps as WorkflowSteps, "LABEL: ${args}", [args], false)
               }
            }
         }
      }
   }
}
