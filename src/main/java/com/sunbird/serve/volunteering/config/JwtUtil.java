package com.sunbird.serve.volunteering.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.Days;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JwtUtil {

    /**
     * Key
     */
    private static final String SECRET = "secret_key";

    /**
     * Expiration time (unit: second)
     **/
    private static final int EXPIRATION = 1;

    /**
     * Generate user token and set token timeout
     *
     * @param userId
     * @return
     */
    public static String createToken(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        return JWT.create()
                // add head
                .withHeader(map)
                // put the users's id
                .withAudience(userId)
                // set expiration date
                .withExpiresAt(Instant.now().plusSeconds(Days.days(EXPIRATION).toStandardSeconds().getSeconds()))
                // issuance time
                .withIssuedAt(Instant.now())
                // secret
                .sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * Get userid
     */
    public static Integer getUserId() {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        // Get the token information from the request header
        String token = request.getHeader("Authorization");
        if (token.length() == 0) {
            return null;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (null != jwt) {
                // Get the information we placed in the token
                List<String> audience = jwt.getAudience();
                if (!audience.isEmpty()) {
                    return Integer.parseInt(audience.get(0));
                }
            }
        } catch (IllegalArgumentException | JWTVerificationException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Verify token and parse token
     */
    public static ResponseResult verify() {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        // Get the token information from the request header
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            return ResponseResult.error(401, "User information has expired，please login again");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (null != jwt) {
                // Get the information we placed in the token
                List<String> audience = jwt.getAudience();
                if (!audience.isEmpty()) {
                    return ResponseResult.success("Authentication is successful", audience.get(0));
                }
            }
        } catch (IllegalArgumentException | JWTVerificationException e) {
            e.printStackTrace();
        }
        return ResponseResult.error(401, "User information has expired, please log in again");
    }
}