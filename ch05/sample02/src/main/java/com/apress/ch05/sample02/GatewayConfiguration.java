package com.apress.ch05.sample02;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class GatewayConfiguration implements EnvironmentAware {

	@Override
	public void setEnvironment(final Environment environment) {
		String keystoreLocation = environment.getProperty("server.ssl.key-store");
		String keystorePassword = environment.getProperty("server.ssl.key-store-password");

		if (keystoreLocation != null && keystorePassword != null) {

			System.setProperty("javax.net.ssl.trustStore", keystoreLocation);
			System.setProperty("javax.net.ssl.trustStorePassword", keystorePassword);

			System.setProperty("javax.net.ssl.keyStore", keystoreLocation);
			System.setProperty("javax.net.ssl.keyStorePassword", keystorePassword);
		}
	}
}