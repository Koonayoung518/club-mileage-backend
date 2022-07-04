package com.club.mileage.backend.serivce;

import com.club.mileage.backend.entity.User;
import com.club.mileage.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public User mockUser(){
        //임의의 유저
        User user = User.builder()
                .nickName("유저")
                .build();
        user = userRepository.save(user);
        return user;
    }
}
