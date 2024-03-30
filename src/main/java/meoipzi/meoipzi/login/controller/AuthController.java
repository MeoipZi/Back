package meoipzi.meoipzi.login.controller;


import meoipzi.meoipzi.login.auth.JwtFilter;
import meoipzi.meoipzi.login.auth.TokenProvider;
import meoipzi.meoipzi.login.dto.LoginDto;
import meoipzi.meoipzi.login.dto.TokenDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    //login
    @PostMapping("/authenticate")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());//LoginDto의 username(email), password를 파라미터로 받아서 username~ 토큰객체 생성

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);//토큰으로 loadUserByUsername 메소드가 실행됨
        SecurityContextHolder.getContext().setAuthentication(authentication);//메서드 실행 결과인 authentication 객체를 SecurityContext에 저장

        String jwt = tokenProvider.createToken(authentication); //인증정보(authentication)를 가지고 jwt 토큰 생성

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt); // 헤더에 토큰 넣음(Bearer 필수)
        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK); // TokenDto를 이용해 responsebody에도 토큰 넣어줌
    }
}
