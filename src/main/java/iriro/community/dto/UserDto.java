package iriro.community.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor // 롬복
public class UserDto {
    private Integer userId; // 아이디(이메일)
    private String pwToken; // 비밀번호
    private String nickName; // 닉네임
    private String createDate; // 가입일
    private String updateDate; // 수정일


}
