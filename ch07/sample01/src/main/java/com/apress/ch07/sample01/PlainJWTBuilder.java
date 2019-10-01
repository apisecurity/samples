package com.apress.ch07.sample01;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;

public class PlainJWTBuilder {

	public static void main(String[] args) throws ParseException {
		parsePlainJWT();
	}

	public static String buildPlainJWT() {

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

		// create plaintext JWT with the JWT claims.
		PlainJWT plainJwt = new PlainJWT(jwtClaims);

		// serialize into string.
		String jwtInText = plainJwt.serialize();

		// print the value of the JWT.
		System.out.println(jwtInText);

		return jwtInText;
	}

	public static PlainJWT parsePlainJWT() throws ParseException {

		// get JWT in base64-encoded text.
		String jwtInText = buildPlainJWT();

		// build a plain JWT from the bade64 encoded text.
		PlainJWT plainJwt = PlainJWT.parse(jwtInText);

		// print the JOSE header in JSON.
		System.out.println(plainJwt.getHeader().toString());

		// print JWT body in JSON.
		System.out.println(plainJwt.getPayload().toString());

		return plainJwt;
	}

}
