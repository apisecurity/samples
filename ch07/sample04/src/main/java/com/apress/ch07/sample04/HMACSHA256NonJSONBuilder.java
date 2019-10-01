package com.apress.ch07.sample04;

import java.text.ParseException;
import java.util.UUID;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;

public class HMACSHA256NonJSONBuilder {

	public static void main(String[] args) throws ParseException, JOSEException {
		// System.out.println(UUID.randomUUID().toString());
		// buildHmacSha256SignedJWT(UUID.randomUUID().toString());
		// isValidHmacSha256Signature();
		buildHmacSha256SignedJWT(UUID.randomUUID().toString());
	}

	public static String buildHmacSha256SignedJWT(String sharedSecretString) throws JOSEException {

		// create an HMAC-protected JWS object with a non-JSON payload
		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload("Hello world!"));

		// create JWS header with HMAC-SHA256 algorithm.
		jwsObject.sign(new MACSigner(sharedSecretString));

		// serialize into base64-encoded text.
		String jwtInText = jwsObject.serialize();

		// print the value of the JWT.
		System.out.println(jwtInText);

		return jwtInText;
	}

}
