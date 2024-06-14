package com.Config;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtProvider {

//     SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());


//     public String getEmailFromToken(String jwt) {
//         jwt=jwt.substring(7);
//         Claims claims=Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();
//         String email=String. valueOf(claims.get("email"));
//         return email;
//     }
}
