package com.github.poulad.jenkins

import hudson.AbortException

class ShellExecutor {
   static def exec(WorkflowSteps steps, String label, String command) throws AbortException {
      steps.sh label: label, script: command, encoding: 'UTF-8', returnStdout: true
   }

   static String exec(WorkflowSteps steps, String label, Iterable<String> commands, boolean hideStdOutErr) throws AbortException {
      def tmpFile = File.createTempFile("script-", ".sh")
      String stdOut = null
      try {
         tmpFile.write(['#!/bin/sh', *commands].join('\n'))
         tmpFile.setExecutable(true, true)
         stdOut = steps.sh label: label, script: tmpFile.absolutePath, encoding: 'UTF-8', returnStdout: hideStdOutErr
      } finally {
         tmpFile.delete() // ToDo
      }

      return stdOut
   }
}
