package com.codependentvariables.cab302groupproject.service;

import com.codependentvariables.cab302groupproject.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> getAll();
    Optional<User> getById(Long id);
    Optional<User> getByEmail(String email);
    User create(User user);
    User update(Long id, User source);
    void delete(User user);
    void deleteById(Long id);
}