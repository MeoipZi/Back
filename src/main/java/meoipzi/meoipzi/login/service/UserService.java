package meoipzi.meoipzi.login.service;


import lombok.RequiredArgsConstructor;
import meoipzi.meoipzi.common.excepiton.NotFoundMemberException;
import meoipzi.meoipzi.login.domain.Authority;
import meoipzi.meoipzi.login.domain.User;
import meoipzi.meoipzi.login.dto.UserDto;
import meoipzi.meoipzi.common.excepiton.DuplicateMemberException;
import meoipzi.meoipzi.login.repository.UserRepository;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import meoipzi.meoipzi.login.util.SecurityUtil;
import java.util.Collections;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDto signup(UserDto userDto) {
        if (userRepository.findOneWithAuthoritiesByUsername(userDto.getUsername()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 존재하는 이메일입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER") //기본 유저는 ROLE_USER 권한만 가지고 있음
                .build();

        User user = User.builder()
                .username(userDto.getUsername())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .authorities(Collections.singleton(authority))
                .activated(true)
                .build();
        return UserDto.from(userRepository.save(user));
    }

    //username(email) 기준 username 과 권한 정보를 가지고 옴
    @Transactional(readOnly = true)
    public UserDto getUserWithAuthorities(String username) {
        return UserDto.from(userRepository.findOneWithAuthoritiesByUsername(username).orElse(null));
    }

    //SecurityContext에 저장된 username만 가져옴 - getCurrentUsername()
    @Transactional(readOnly = true)
    public UserDto getMyUserWithAuthorities() {
        return UserDto.from(
                SecurityUtil.getCurrentUsername()
                        .flatMap(userRepository::findOneWithAuthoritiesByUsername)
                        .orElseThrow(() -> new NotFoundMemberException("Member not found"))
        );
    }
}