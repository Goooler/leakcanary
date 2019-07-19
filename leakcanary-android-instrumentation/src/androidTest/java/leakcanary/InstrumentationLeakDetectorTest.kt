package leakcanary

import leakcanary.TestUtils.assertLeak
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.Date

/**
 * Tests that the [InstrumentationLeakDetector] can detect leaks
 * in instrumentation tests
 */
class InstrumentationLeakDetectorTest {

  @Before fun setUp() {
    LeakSentry.objectWatcher
        .clearWatchedObjects()
  }

  @After fun tearDown() {
    LeakSentry.objectWatcher
        .clearWatchedObjects()
  }

  @Test fun detectsLeak() {
    leaking = Date()
    val refWatcher = LeakSentry.objectWatcher
    refWatcher.watch(leaking)
    assertLeak(Date::class.java)
  }

  companion object {
    private lateinit var leaking: Any
  }
}
