package sample.pojo.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:sample/pojo/test/applicationContext4Test.xml" })
@ActiveProfiles("standalone")
public abstract class BaseTest extends TestCase {
	protected Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
	private static final String PATTERN_FORMAT = "%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %-40([%thread] %F:%L) %msg%n";
	protected Level defaultLevel = Level.INFO;

	@Before
	public void myBefore() {
		MockitoAnnotations.initMocks(this);
		settingLog();
	}

	private void settingLog() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		PatternLayoutEncoder layout = new PatternLayoutEncoder();
		layout.setPattern(PATTERN_FORMAT);
		layout.setContext(lc);
		layout.start();
		root.setLevel(defaultLevel);
	}
}
