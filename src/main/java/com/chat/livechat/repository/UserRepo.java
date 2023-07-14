package com.chat.livechat.repository;


import com.chat.livechat.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    public Optional<User> findByUsername(String name);
    public Optional<User> findByDisplayName(String name);
}
