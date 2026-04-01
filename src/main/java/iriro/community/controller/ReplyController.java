package iriro.community.controller;

import iriro.community.dto.ReplyDto;
import iriro.community.service.JWTService;
import iriro.community.service.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class ReplyController {

    @Autowired
    private final ReplyService replyService;
    private final JWTService jwtService;

    // 1. 댓글 등록
    // http://localhost:8080/board/rpwrite
    // Bearer eyJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InNvc29AbmF2ZXIuY29tIiwiaWF0IjoxNzc0OTM3NjY1LCJleHAiOjE3NzUwMjQwNjV9.e2i3Yl9CpiEjvSH-uAwxy1pINyKvrbzHt0XNGwPS7Ws
    // { "replyContent" : "박진감보고싶습니감ㅠㅠ" , "boardId" : 2 }
    @PostMapping("/rpwrite")
    public ResponseEntity<?> rpAdd(@RequestBody ReplyDto replyDto, @RequestHeader(value = "Authorization",required = false) String token) {

        // 기본값=비회원 이메일
        String loginEmail = "iriro@google.com";

        if(token != null || !token.startsWith("Bearer ")) {
            String realToken = token.replace("Bearer", "");
            String realEmail = jwtService.getClaim(realToken);
            if (realEmail != null) {
                loginEmail = realEmail;
            }
        }
        boolean result = replyService.rpAdd(replyDto, loginEmail);
        return ResponseEntity.ok(result);
    }

    // 2. 댓글 삭제
    // http://localhost:8080/board/rpdelete?replyId=1
    @DeleteMapping("/rpdelete")
    public ResponseEntity<?> rpDelete(@RequestParam Integer replyId, HttpServletRequest request) {
        // 요청헤더에서 Authorization 토큰 꺼내기.
        String token = request.getHeader("Authorization");
        // 2. JWTService를 이용해 토큰 안의 이메일 추출
        String loginEmail = jwtService.getClaim(token);

        // 서비스한테 삭제하라고 고함지름
        boolean result = replyService.rpDelete(replyId, loginEmail);
        return ResponseEntity.ok(result);
        }

    }