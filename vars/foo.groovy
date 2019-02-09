import com.github.poulad.jenkins.ShellExecutor

def call(String args) {
   echo args
   ShellExecutor.exec(sh,
      'My Script Title',
      ['echo foo', 'ls', "echo ${args}"] as String[],
      true
   )
}
