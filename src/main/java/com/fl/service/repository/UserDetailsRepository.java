package com.fl.service.repository;

import com.fl.service.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserDetailsRepository extends CrudRepository<User, Integer> {
    public List<User> findByEmail(String email);
}
