package com.example.demo.User;

import jakarta.persistence.NoResultException;

import java.sql.SQLException;
import java.util.List;

public class UserService {
    private UserRepository userRepository = new UserRepository();

    public UserEntity addUser(UserEntity userEntity) throws SQLException {
        return userRepository.create(userEntity);
    }

    public List<UserEntity> getAllUsers() throws SQLException {
        return userRepository.findAll();
    }

    public UserEntity getUserById(Long id) throws SQLException {
        var user = userRepository.findByID(id);
        if (user == null) {
            throw new IllegalArgumentException("User does not exist in database");
        } else {
            return user;
        }
    }

    public UserEntity updateUser(Long id, UserEntity userEntity) throws SQLException {
        UserEntity user = userRepository.findByID(id);
        if (user == null) {
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        user = userEntity;
        return userRepository.updateById(id, user);
    }

    public void deleteUserById(Long id) throws SQLException {
        userRepository.deleteByID(id);
    }

    public boolean existsUserByEmail(String email) throws SQLException {
        var user = userRepository.findByEmail(email);
        boolean found;
        try{
            found = user.getEmail()!=null;
            System.out.println("aici");
        }catch (NullPointerException e){
            System.out.println("aivi");
            return false;
        }
        return true;
    }

    public boolean existsUserByName(String name) throws SQLException {
        var user = userRepository.findByName(name);
        boolean found;
        try{
            found = user.getEmail()!=null;
            System.out.println("aici");
        }catch (NullPointerException e){
            System.out.println("aivi");
            return false;
        }
        return true;
    }

    public UserEntity findByName(String name) throws SQLException {
        return userRepository.findByName(name);
    }

    public UserEntity findByEmail(String email) throws SQLException {
        return userRepository.findByEmail(email);
    }
}
