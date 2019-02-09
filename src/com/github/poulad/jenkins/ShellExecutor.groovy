package com.github.poulad.jenkins

import java.io.File

class ShellExecutor {
   static def exec(steps, String label, String command) {
      steps.sh label: label, script: command, encoding: 'UTF-8', returnStdout: true
   }

   static String exec(steps, String label, Iterable<String> commands, boolean hideStdOutErr) {
      def tmpFile = File.createTempFile("script-", ".sh")
      String stdOut = null
      try {
         tmpFile.write('#!/bin/sh\n' + commands.join('\n'))
         tmpFile.setExecutable(true, true)
         stdOut = steps.sh label: label, script: tmpFile.absolutePath, encoding: 'UTF-8', returnStdout: hideStdOutErr
      } finally {
         tmpFile.delete() // ToDo
      }

      return stdOut
   }
}
