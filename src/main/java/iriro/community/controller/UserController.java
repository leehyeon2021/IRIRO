package iriro.community.controller;

import iriro.community.dto.UserDto;
import iriro.community.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired // 주방장을 불러오거라
    private UserService userService;

    // 1. 회원가입 post = create = save
    @PostMapping("/join")
    // http://localhost:8080/user/join
    // { "email" : "soso@sadsa.com","pwToken" : "1234","nickName" : "박진감"}
    public ResponseEntity<?> join(@RequestBody UserDto userDto){
        return ResponseEntity.ok( userService.join(userDto));
    }
}
