package iriro.community.controller;

import iriro.community.dto.ReplyDto;
import iriro.community.entity.ReplyEntity;
import iriro.community.repository.ReplyRepository;
import iriro.community.service.JWTService;
import iriro.community.service.ReplyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private final ReplyService replyService;
    private final JWTService jwtService;

    // 1. 댓글 등록
    // http://localhost:8080/reply/rpwrite
    // { "replyContent" : "박진감보고싶습니감ㅠㅠ" , "boardId" : 2 }
    @PostMapping("/rpwrite")
    public ResponseEntity<?> rpAdd(@RequestBody ReplyDto replyDto, @RequestHeader("Authorization")String token) {

        // 기본값을 null로 시작(비회원 상태)

        String loginEmail = null;

        if(token != null && token.startsWith("Bearer")) {
            String realToken = token.substring(7);
            loginEmail = jwtService.getClaim(realToken);
        }
        boolean result = replyService.rpAdd(replyDto, loginEmail);
        return ResponseEntity.ok(result);
    }

    // 2. 댓글 삭제
    // http://localhost:8080/reply/rpdelete?replyId=1
    @DeleteMapping("/rpdelete")
    public ResponseEntity<?> rpDelete(@RequestParam Integer replyId, HttpSession session) {
        Object object = session.getAttribute("email");
        if (object == null) { return ResponseEntity.ok(false);}
            String loginEmail = (String)object;
            boolean result = replyService.rpDelete(replyId, loginEmail);
            return ResponseEntity.ok(result);
        }
    }