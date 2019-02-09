package com.github.poulad.jenkins

import org.junit.Test

class ShellExecutorTest {
   @Test
   void should_execute_shell_command_once() {
      int callCount = 0
      Closure sh = { String cmd ->
         callCount++
         assert cmd == 'some text'
      }

      ShellExecutor.exec(sh, "some text")

      assert callCount == 1
   }

   @Test
   void should_execute_shell_command_once2() {
      int callCount = 0
      Closure sh = { String arg ->
         callCount++

         def cmdLines = arg.split('\n')
         assert cmdLines.size() == 2
         assert cmdLines[0] == '# Example Title - '
         assert cmdLines[1] ==~ '^/tmp/script-.+\\.sh$'

         def file = new File(cmdLines[1])
         assert file.exists()
         assert file.readLines() == [
            '#!/bin/sh',
            'a sample > /dev/null 2>&1',
            'test > /dev/null 2>&1',
            'command > /dev/null 2>&1'
         ]
      }

      ShellExecutor.exec(sh, 'Example Title', ['a sample', 'test', 'command'] as String[], true)

      assert callCount == 1
   }
}
