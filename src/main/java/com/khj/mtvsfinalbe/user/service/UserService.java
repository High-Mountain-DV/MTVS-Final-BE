package com.khj.mtvsfinalbe.user.service;

import com.khj.mtvsfinalbe.user.domain.User;
import com.khj.mtvsfinalbe.user.domain.dto.UserRequestDTO;
import com.khj.mtvsfinalbe.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserRequestDTO.signDTO dto) {
        validatePassword(dto.password(), dto.confirmPassword());
        duplicatedLoginId(dto.loginId());

            User user = User.builder()
                    .loginId(dto.loginId())
                    .password(passwordEncoder.encode(dto.password()))
                    .nickname(dto.nickname())
                    .build();
            userRepository.save(user);
    }

    private void validatePassword(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new IllegalArgumentException("비밀번호를 다시 확인해주세요.");
        }
    }

    private void duplicatedLoginId(String loginId) {
        User user = userRepository.findByLoginId(loginId);
        if (user != null) {
            throw new IllegalArgumentException("중복된 아이디입니다.");
        }
    }
}
