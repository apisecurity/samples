package com.apress.ch07.sample02;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

public class HMACSHA256JWTBuilder {

	public static void main(String[] args) throws ParseException, JOSEException {
		String sharedSecretString = "ea9566bd-590d-4fe2-a441-d5f240050dbc";

		isValidHmacSha256Signature(buildHmacSha256SignedJWT(sharedSecretString));
		// isValidHmacSha256Signature();
		// signAnyPayload();
	}

	public static String buildHmacSha256SignedJWT(String sharedSecretString) throws JOSEException {

		// build audience restriction list.
		List<String> aud = new ArrayList<String>();
		aud.add("https://app1.foo.com");
		aud.add("https://app2.foo.com");

		Date currentTime = new Date();

		// create a claim set.
		JWTClaimsSet jwtClaims = new JWTClaimsSet.Builder().
		// set the value of the issuer.
				issuer("https://apress.com").
				// set the subject value - JWT belongs to this subject.
				subject("john").
				// set values for audience restriction.
				audience(aud).
				// expiration time set to 10 minutes.
				expirationTime(new Date(new Date().getTime() + 1000 * 60 * 10)).
				// set the valid from time to current time.
				notBeforeTime(currentTime).
				// set issued time to current time.
				issueTime(currentTime).
				// set a generated UUID as the JWT identifier.
				jwtID(UUID.randomUUID().toString()).build();

		// create JWS header with HMAC-SHA256 algorithm.
		JWSHeader jswHeader = new JWSHeader(JWSAlgorithm.HS256);

		// create signer with the provider shared secret.
		JWSSigner signer = new MACSigner(sharedSecretString);

		// create the signed JWT with the JWS header and the JWT body.
		SignedJWT signedJWT = new SignedJWT(jswHeader, jwtClaims);

		// sign the JWT with HMAC-SHA256.
		signedJWT.sign(signer);

		// serialize into base64-encoded text.
		String jwtInText = signedJWT.serialize();

		// print the value of the JWT.
		System.out.println(jwtInText);

		return jwtInText;
	}

	public static void signAnyPayload() throws KeyLengthException, JOSEException {
		// Create an HMAC-protected JWS object with some payload
		JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload("Hello world!"));

		// We need a 256-bit key for HS256 which must be pre-shared
		byte[] sharedKey = new byte[32];
		new SecureRandom().nextBytes(sharedKey);

		// Apply the HMAC to the JWS object
		jwsObject.sign(new MACSigner(sharedKey));

		// Output to URL-safe format
		System.out.println(jwsObject.serialize());
	}

	public static boolean isValidHmacSha256Signature(String jwtInText) throws JOSEException, ParseException {

		String sharedSecretString = "ea9566bd-590d-4fe2-a441-d5f240050dbc";

		// create verifier with the provider shared secret.
		JWSVerifier verifier = new MACVerifier(sharedSecretString);

		// create the signed JWT with the base64url-encoded text.
		SignedJWT signedJWT = SignedJWT.parse(jwtInText);

		// verify the signature of the JWT.
		boolean isValid = signedJWT.verify(verifier);

		if (isValid) {
			System.out.println("valid JWT signature");
		} else {
			System.out.println("invalid JWT signature");
		}

		return isValid;
	}

}
