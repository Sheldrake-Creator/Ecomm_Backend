package com.response;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dto.UserDTO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;

@RequiredArgsConstructor
@Component
public class UserAuthProvider {

    @Value("${-security, jwt token secret-key: secret-key}")
    private String secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UserDTO userDTO) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + 43_200_000);
        return JWT.create().withIssuer(userDTO.getUserName()).withIssuedAt(now).withExpiresAt(validity)
                .withClaim("email", userDTO.getEmail()).sign(Algorithm.HMAC256(secretKey));
    }

    public Authentication validateToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded = verifier.verify(token);
        UserDTO user = new UserDTO();
        user.setUserName(decoded.getIssuer());
        user.setEmail(decoded.getClaim("email").asString());
        return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
    }

    public String getUserNameFromToken(String jwt) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        JWTVerifier verifier = JWT.require(algorithm).build();
        System.out.println("Token Util Start!");
        String token = jwt;
        String accessToken = token.replace("Bearer ", ""); // delete Bearer
        System.out.println("JWT : " + accessToken); // TokenValue
        DecodedJWT decoded = verifier.verify(accessToken);
        // * TODO fix this shit later. Authentication needs to be stricter */
        return decoded.getIssuer();
    }

}
