package com.example.demo.User;

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
}
