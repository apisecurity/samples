package com.apress.ch05.sample02;

import java.io.File;

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

			String path = System.getProperty("user.dir");

			System.setProperty("javax.net.ssl.trustStore", path + File.separator + keystoreLocation);
			System.setProperty("javax.net.ssl.trustStorePassword", keystorePassword);

			System.setProperty("javax.net.ssl.keyStore", path + File.separator + keystoreLocation);
			System.setProperty("javax.net.ssl.keyStoreType", "jks");
			System.setProperty("javax.net.ssl.keyStorePassword", keystorePassword);

			System.out.println("Setting keystore properties...");
		}
	}
}