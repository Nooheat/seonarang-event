package com.nooheat.seonarangevent.support;

import com.google.gson.JsonObject;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.exception.UidNotFoundException;
import io.jsonwebtoken.*;

public class JwtManager {

    static String secret = System.getenv("SEONARANG_EVENT_SECRET");

    static {
        if (secret == null) secret = "this is default secret";
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


    //TODO:: Generate Jwt Token By User Object
    public static String generateJwtToken(User user) {

        return Jwts.builder()
                .claim("uid", user.getUid())
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

}
