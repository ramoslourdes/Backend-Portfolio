package com.Portfolio.myPortfolio.security;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    @Autowired UserRepository usuRepo;

    public User getByEmail(String email){
        return usuRepo.findByEmail(email);
    }

    public void save(User user){
        usuRepo.save(user);
    }
}