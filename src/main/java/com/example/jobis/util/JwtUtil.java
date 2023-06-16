package com.example.jobis.util;


import com.google.gson.reflect.TypeToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.GsonJsonParser;


import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class JwtUtil {

    static final String PUPBLIC_KEY = "삼쩜삼 백엔드 엔지니어 채용 과제";
    static final Key key = Keys.hmacShaKeyFor(PUPBLIC_KEY.getBytes(StandardCharsets.UTF_8));

    public static String makeMemberJwtToken(String json){

        String jwtToken = Jwts.builder()
                .setClaims(JsonUtil.toMap(json))
                .compact();

        return jwtToken;
    }

    public static String readJwtTokenBody(String token){

        String body = Jwts.parserBuilder()
                .build()
                .parse(token)
                .getBody()
                .toString();

        return body;
    }


}
