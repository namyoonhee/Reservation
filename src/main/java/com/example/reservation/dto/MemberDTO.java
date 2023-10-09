package com.example.reservation.dto;

import com.example.reservation.entity.MemberEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        return memberDTO;
    }
}
// private 를 사용하기 위해서는 @Getter, @Setter 등 메서드를 이용해서 간접적으로 필드 값을 사용하도록 함
// lombok 이라는 라이브러리는 어노테이션을 붙쳐줌으로 인해서 각각의 필드에 대한 게터를 자동으로 만들어줌
