package com.codependentvariables.cab302groupproject.service;

import com.codependentvariables.cab302groupproject.model.User;
import com.codependentvariables.cab302groupproject.repository.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(Long id, User source) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setName(source.getName());
            existingUser.setEmail(source.getEmail());
            return existingUser;
        }).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @Override
    public void delete(User user) {
        deleteById(user.getId());
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
