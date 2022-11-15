package com.example.sherlockescape.repository;

import com.example.sherlockescape.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String username);
    Optional<Member> findByUserEmail(String kakaoEmail);
}
