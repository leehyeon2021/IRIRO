package iriro.community.service;

import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;

@Service
public class JwtService {
    private String secret = "qkrwlsrkatkfkdgoqkrwlsrkatkfkdgoqkrwlsrkatkfkdgo"
    private Key secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); // 비밀키 생성
    // [1] 토큰 발급
    public String createToken(String )
}
