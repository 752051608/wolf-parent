package com.wolf.common.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JwtToken生成的工具类
 * * JWT token的格式：header.payload.signature
 * * header的格式（算法、token的类型）：
 * * {"alg": "HS512","typ": "JWT"}
 * * payload的格式（用户名、创建时间、生成时间）：
 * * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * * signature的生成算法：
 * * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 *
 * @author honglou
 * @date 2020-01-09 10:06
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "name";
    @Value("${jwt.secret-key}")
    private String jwtSecretKey;
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据负责生成JWT的token,设置90天过期
     */
    public String generateToken(Long id, String name) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(KEY_ID, id);
        claims.put(KEY_USERNAME, name);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    public String generateToken(Claims claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    /**
     * 从token中获取JWT中的负载
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(jwtSecretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            LOGGER.info("JWT格式验证失败:{}", token);
        }
        return claims;
    }

    /**
     * 生成token的过期时间
     */
    private Date generateExpirationDate() {
        return Date.from(Instant.now().plus(expiration, ChronoUnit.DAYS));
    }

    /**
     * 从token中获取用户id
     */
    public Long getUserIdFromToken(String token) throws ExpiredJwtException {
        Integer userId;
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        //获取用户名称
        userId = (Integer) claims.get(KEY_ID);
        return Long.parseLong(userId.toString());
    }

    /**
     * 从token中获取用户名
     */
    public String getUserNameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            //获取用户名称
            username = (String) claims.get(KEY_USERNAME);
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * 从token中获取Claims
     */
    public Claims getClaims(String token) {
        Claims claims;
        try {
            claims = getClaimsFromToken(token);
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    /**
     * 判断token是否已经失效
     */
    private boolean isTokenExpired(String token) {
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }


    /**
     * 当原来的token没过期时是可以刷新的
     *
     * @param oldToken 带tokenHead的token
     */
    public String refreshHeadToken(String oldToken) {
        if (StringUtils.isEmpty(oldToken)) {
            return null;
        }
        Claims claims = getClaimsFromToken(oldToken);
        return generateToken(claims);
    }


}
