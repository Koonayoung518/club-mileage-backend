package com.club.mileage.backend.serivce;

import com.club.mileage.backend.entity.PointTotal;
import com.club.mileage.backend.entity.Users;
import com.club.mileage.backend.repository.PointTotalRepository;
import com.club.mileage.backend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UsersRepository usersRepository;
    private final PointTotalRepository pointTotalRepository;

    @Transactional
    public Users mockUser(){
        //임의의 유저
        Users users = Users.builder()
                .nickname("유저")
                .build();
        users = usersRepository.save(users);

        PointTotal pointTotal = PointTotal.builder()
                .total(0L)
                .users(users)
                .build();
        pointTotalRepository.save(pointTotal);
        users.updatePointTotal(pointTotal);
        return users;
    }
}
