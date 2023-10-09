package com.example.reservation.repository;

import com.example.reservation.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<MemberEntity,  Long> {
    // 이메일로 회원 정보를 조회 (select * from member_table where member_email=?)
    Optional<MemberEntity> findByMemberEmail(String  memberEmail);
}
