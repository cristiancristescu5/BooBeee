package com.example.demo.User;

import jakarta.persistence.NoResultException;

import java.util.List;

public class UserService {
    private UserRepository userRepository = new UserRepository();
    public UserEntity addUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }
    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }
    public UserEntity getUserById(Long id){
        var user = userRepository.findByID(id);
        if(user == null){
            throw new IllegalArgumentException("User does not exist in database");
        }else{
            return user;
        }
    }
    public UserEntity updateUser(Long id, UserEntity userEntity){
        UserEntity user = userRepository.findByID(id);
        if(user == null){
            throw new IllegalArgumentException("Entity with this id does not exist");
        }
        user = userEntity;
        return userRepository.updateById(id, user);
    }
    public void deleteUserById(Long id){
        userRepository.deleteByID(id);
    }
    public boolean existsUserByEmail(String email){
        try {
            var user = userRepository.findByEmail(email);
        }catch (NoResultException e){
            return false;
        }
        return true;
    }
    public boolean existsUserByName(String name){
//        try{
        try{
            var user = userRepository.findByName(name);
        }catch (NoResultException e){
            return false;
        }
        return true;
    }
    public UserEntity findByName(String name){
        return userRepository.findByName(name);
    }
    public UserEntity findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
