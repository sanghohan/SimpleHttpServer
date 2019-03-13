package com.config;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

public class ConfigTest {

	@Test
	public void loadConfigTest() {

		Config config = ConfigGetter.getConfig("test");
		assertEquals(true, (config != null) ? true : false);
	}
}
