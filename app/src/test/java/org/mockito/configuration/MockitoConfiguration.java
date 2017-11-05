package org.mockito.configuration;

public class MockitoConfiguration extends DefaultMockitoConfiguration {

	//This is needed to run several test files on a row
	@Override
	public boolean enableClassCache() {
		return false;
	}
}