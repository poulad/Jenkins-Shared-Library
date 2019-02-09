package com.github.poulad.jenkins

import groovy.mock.interceptor.MockFor
import static groovy.test.GroovyAssert.*
import hudson.AbortException
import org.junit.Test

class ShellExecutorTest {
   @Test
   void should_invoke_shell() {
      final String LABEL = 'Test Label'
      final String COMMAND = 'test command'

      def mock = new MockFor(WorkflowSteps)
      mock.demand.sh {
         assert it.label == LABEL
         assert it.script == COMMAND
      }
      mock.use {
         def steps = new WorkflowSteps()
         ShellExecutor.exec(steps, LABEL, COMMAND)
      }
      mock.expect.verify()
   }

   @Test
   void should_hide_shell_output() {
      final String LABEL = 'Command Description'
      final String COMMAND0 = 'echo foo'
      final String COMMAND1 = 'ls -l'

      def mock = new MockFor(WorkflowSteps)
      mock.demand.sh {
         assert it.returnStdout == true
         assert it.encoding == 'UTF-8'
         assert it.label == LABEL
         assert it.script ==~ '^/tmp/script-.+\\.sh$'
      }
      mock.use {
         def steps = new WorkflowSteps()
         def stdOut = ShellExecutor.exec(steps, LABEL, [COMMAND0, COMMAND1], true)
         assert stdOut == null
      }
      mock.expect.verify()
   }

   @Test
   void should_fail_when_shell_script_fails() {
      final String ERROR_MESSAGE = 'script returned exit code 127'

      def mock = new MockFor(WorkflowSteps)
      mock.demand.sh {
         throw new AbortException(ERROR_MESSAGE)
      }
      mock.use {
         def steps = new WorkflowSteps()
         def e = shouldFail(AbortException) {
            ShellExecutor.exec(steps, '', 'an-invalid-command')
         }
         assert e.message == ERROR_MESSAGE
      }
      mock.expect.verify()
   }

   @Test
   void should_fail_when_shell_script_fails2() {
      final String ERROR_MESSAGE = 'script returned exit code 127'

      def mock = new MockFor(WorkflowSteps)
      mock.demand.sh {
         throw new AbortException(ERROR_MESSAGE)
      }
      mock.use {
         def steps = new WorkflowSteps()
         def e = shouldFail(AbortException) {
            ShellExecutor.exec(steps, '', ['an-invalid-command'], false)
         }
         assert e.message == ERROR_MESSAGE
      }
      mock.expect.verify()
   }
}
