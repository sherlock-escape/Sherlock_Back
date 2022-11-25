package com.example.sherlockescape.controller;

import com.example.sherlockescape.dto.request.KakaoUserInfoDto;
import com.example.sherlockescape.service.KakaoUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class SocialLoginController {
	private final KakaoUserService kakaoUserService;

	// 카카오 로그인
	@GetMapping("/kakao/callback")
	public KakaoUserInfoDto kakaoLogin(@RequestParam(name="code") String code, HttpServletResponse response) throws JsonProcessingException {
		return kakaoUserService.kakaoLogin(code, response);
	}

//	@GetMapping("/kakao/callback")
//	public ResponseEntity<?> kakaoLogin(@RequestParam String code)
//			throws IOException {
//		SocialUserDto socialUserDto = kakaoUserService.kakaoLogin(code);
//		return ResponseEntity.ok()
//				.headers(MemberUtil.getTokenHeaders(socialUserDto.getTokenDto()))
//				.body(socialUserDto.getMemberDto());
//	}
}