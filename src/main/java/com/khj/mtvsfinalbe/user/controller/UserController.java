package com.khj.mtvsfinalbe.user.controller;

import com.khj.mtvsfinalbe._core.utils.ApiUtils;
import com.khj.mtvsfinalbe._core.utils.JwtUtil;
import com.khj.mtvsfinalbe.user.domain.User;
import com.khj.mtvsfinalbe.user.domain.dto.UserRequestDTO;
import com.khj.mtvsfinalbe.user.domain.dto.UserResponseDTO;
import com.khj.mtvsfinalbe.user.repository.UserRepository;
import com.khj.mtvsfinalbe.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserRequestDTO.loginDTO dto){
        User user = userRepository.findByLoginId(dto.loginId());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getId(), dto.password())
            );
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ApiUtils.error("사용자의 이름 또는 비밀번호가 잘못되었습니다."), HttpStatus.UNAUTHORIZED);
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(user.getId()));
        String jwtToken = jwtUtil.generateToken(userDetails.getUsername());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + jwtToken);

        UserResponseDTO userResponseDTO = new UserResponseDTO(user.getId(), user.getNickname(), user.getLoginId());

        return new ResponseEntity<>(
                ApiUtils.success(userResponseDTO)
                , headers
                , HttpStatus.OK
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRequestDTO.signDTO dto) {
        try {
            userService.save(dto);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(ApiUtils.error(e.getMessage()), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(ApiUtils.error(e.getMessage()), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(ApiUtils.success("회원가입이 완료되었습니다."), HttpStatus.OK);
    }
}
