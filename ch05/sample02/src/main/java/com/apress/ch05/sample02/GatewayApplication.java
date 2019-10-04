package com.apress.ch05.sample02;

import java.io.File;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@EnableZuulProxy
@SpringBootApplication
//@EnableResourceServer
public class GatewayApplication {
	
	static {
		
		String path = System.getProperty("user.dir");
		
		System.setProperty("javax.net.ssl.trustStore", path + File.separator +"keystore.jks");
		System.setProperty("javax.net.ssl.trustStorePassword", "springboot");

		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				System.out.println("PEER VERIFICATION PEER VERIFICATION  PEER VERIFICATION ");
				return true;
			}
		});
	}

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
    
}
