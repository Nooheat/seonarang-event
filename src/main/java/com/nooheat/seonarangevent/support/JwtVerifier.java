package com.nooheat.seonarangevent.support;

import com.nooheat.seonarangevent.exception.UidNotFoundException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class JwtVerifier {

    static String secret = System.getenv("SEONARANG_EVENT_SECRET");

    static {
        if(secret == null) secret = "this is default secret";
    }

    public static boolean isAuthenticToken(String tokenStr) throws Exception {

        Jws<Claims> token = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(tokenStr);

        String uid = token.getBody().get("uid", String.class);

        if (uid == null) {
            throw new UidNotFoundException();
        }

        return true;
    }

    public static String generateJwtToken(String uid) {
        return uid;
    }

}
