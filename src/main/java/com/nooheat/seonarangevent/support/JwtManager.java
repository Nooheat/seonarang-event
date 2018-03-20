package com.nooheat.seonarangevent.support;

import com.google.gson.JsonObject;
import com.nooheat.seonarangevent.domain.user.User;
import com.nooheat.seonarangevent.exception.JwtTokenClaimNotFoundException;
import io.jsonwebtoken.*;

public class JwtManager {

    static String secret = System.getenv("SEONARANG_EVENT_SECRET");

    static {
        if (secret == null) secret = "this is default secret";
    }

    public static Jws<Claims> parse(String tokenStr) throws JwtTokenClaimNotFoundException {

        Jws<Claims> token = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(tokenStr);

        String uid = token.getBody().get("uid", String.class);
        Boolean permission = token.getBody().get("permission", Boolean.class);

        if (uid == null) {
            throw new JwtTokenClaimNotFoundException("uid");
        } else if (permission == null) {
            throw new JwtTokenClaimNotFoundException("permission");
        }

        return token;
    }


    //TODO:: Generate Jwt Token By User Object
    public static String generateJwtToken(User user) {
        System.out.println(user.getDisplayName());
        return Jwts.builder()
                .claim("uid", user.getUid())
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

}
