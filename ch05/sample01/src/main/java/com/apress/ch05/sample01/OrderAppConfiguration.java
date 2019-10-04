package com.apress.ch05.sample01;

import java.io.File;

import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class OrderAppConfiguration implements EnvironmentAware {

	@Override
	public void setEnvironment(final Environment environment) {
		String truststoreLocation = environment.getProperty("server.ssl.key-store");
		String truststorePassword = environment.getProperty("server.ssl.key-store-password");

		if (truststoreLocation != null && truststorePassword != null) {
			String path = System.getProperty("user.dir");

			System.setProperty("javax.net.ssl.trustStore", path + File.separator + truststoreLocation);
			System.setProperty("javax.net.ssl.trustStorePassword", truststorePassword);
		}

	}
}