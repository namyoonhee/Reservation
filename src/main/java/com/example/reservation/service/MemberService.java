package com.example.reservation.service;

import com.example.reservation.dto.MemberDTO;
import com.example.reservation.entity.MemberEntity;
import com.example.reservation.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        // 1. dto -> entity 변환 (entity  클래스에  클래스를 만들어줌)
        // 2. repository 의 save 메서드 호출
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);  //  메서드 이름은 save 빡에 안됨
        // repository 의 save 메서드 호출 ( 조건. entity 객체를 넘겨줘야 함)
    }

    public MemberDTO login(MemberDTO memberDTO) {
        /*
        1 . 회원이 입력한 이메일로 DB에서 조회를 함
        2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        // DTO 에 담겨있는 이메일 값을 넘겨준다.
        if (byMemberEmail.isPresent()) {
            // 조회 결과가 있다 (해당 이메일을 가진 회원 정보가 있다.)
            MemberEntity memberEntity = byMemberEmail.get(); // get (가져오기)
            if (memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())) {
                // memberEntity.getMemberPassword() 와 equals(memberDTO.getMemberPassword() 가 같은지 비교 (== 는 사용 X)
                //  비밀번호 일치
                // entity -> dto  변환 후 리턴 (DTO 클래스에
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity); // 엔티티가 하나고 그거를 dto 하나로 변환을 하는 것
                return dto;
            } else {
                // 비밀번호 불일치 ( 로그인 실패)
                return null;
            }
        } else {
            //  조회 결과가 없다 ( 해당 이메일을 가진 회원이 없다.)
            return null;  // 로그인 실패
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>(); // 리턴을 해줄 리스트 객체 선언
        // 엔티티가 여러개 담김 리스트 객체를 dto가 여러개 담긴 리스트 객체로 옮겨 담는 것 (리스트에서 리스트로)
        // 하나 하나 씩 옮겨 닮는 과정 필요 (for문 사용)

        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toMemberDTO(memberEntity);
//            return memberDTO; // 이 세줄을 (1)
            return MemberDTO.toMemberDTO(optionalMemberEntity.get()); // 1줄로 표현
        } else {
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if (optionalMemberEntity.isPresent()) {
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        } else {
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO));
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }
}
