package com.github.poulad.jenkins

class ShellExecutor {
   static void exec(Closure sh, String command) {
      sh command
   }

   static void exec(Closure sh, String title, String[] commands, boolean hideStdOutErr) {
      String cmd
      if (hideStdOutErr) {
         cmd = commands.collect { it += " > /dev/null 2>&1" }.join('\n')
      } else {
         cmd = commands.join('\n')
      }

      def tmpFile = File.createTempFile("script-", ".sh")
      try {
         tmpFile.write("#!/bin/sh\n${cmd}")
         tmpFile.setExecutable(true, true)
         sh "# ${title} - \n${tmpFile.absolutePath}"
      } finally {
         tmpFile.delete() // ToDo
      }
   }
}
