package cn.kcyf.security.util;

import cn.kcyf.commons.utils.DateUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Slf4j
public class JWTTokenUtils {

    public final static String X_ACCESS_TOKEN_KEY = "x-access-token";

    /**
     * 生成token
     * 默认过期时间1小时
     * @param username
     * @param secrte
     * @return
     * iss: jwt签发者
     * sub: jwt所面向的用户
     * aud: 接收jwt的一方
     * exp: jwt的过期时间，这个过期时间必须要大于签发时间
     * nbf: 定义在什么时间之前，该jwt都是不可用的.
     * iat: jwt的签发时间
     * jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击
     */
    public static String sign(String username, String secrte) {
        Date date = new Date();
        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        // 附带username信息
        return JWT.create()
                .withHeader(map)
                .withClaim("iss", "service")
                .withSubject("user")
                .withClaim("username", username)
                .withIssuedAt(date)
                .withExpiresAt(DateUtils.addTime(date, Calendar.HOUR,1))
                .sign(Algorithm.HMAC256(secrte));
    }

    /**
     * 校验token是否正确
     *
     * @param token
     * @param username
     * @param secrte
     * @return 是否正确
     */
    public static Map<String, Claim> verify(String token, String username, String secrte) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secrte))
                .withClaim("username", username)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaims();
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @param token
     * @return
     */
    public static String getUsername(String token) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("username").asString();
    }

}


