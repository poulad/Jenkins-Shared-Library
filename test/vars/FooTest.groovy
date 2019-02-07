package vars

import com.lesfurets.jenkins.unit.BaseRegressionTest
import org.junit.Before
import org.junit.Test

class FooTest extends BaseRegressionTest {

  @Override
  @Before
  void setUp() throws Exception {
    super.setUp()

    callStackPath = "test/vars/callstacks/"
  }

  @Test
  void configured() throws Exception {
    def script = loadScript('vars/foo.groovy')
    script.call('bar')

    printCallStack()
    testNonRegression("configured")
  }
}
