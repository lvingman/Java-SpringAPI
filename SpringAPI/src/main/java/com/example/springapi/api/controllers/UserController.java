package com.example.springapi.api.controllers;

import com.example.springapi.api.models.User;
import com.example.springapi.dao.repositories.UserRepository;
import com.example.springapi.services.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserRepository userRepository;


    public UserController(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    //GET BY ID
   @GetMapping("/{id}")
   public User getUserById(@PathVariable("id") Integer id){
        Optional<User> user = userRepository.findById(id);
        if(user.isPresent()){
            return user.get();
        }
        throw new UserNotFoundException();
   }

    //GET
    @GetMapping()
    public List<User> getUsers(){
        Optional<List<User>> users = userRepository.findAll();
        if (users.isPresent()){
            return users.get();
        }
        throw new UserNotFoundException();
    }

    //POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUser(@RequestBody User user) {
        userRepository.create(user);
    }

    //PUT
   @ResponseStatus(HttpStatus.NO_CONTENT)
   @PutMapping("/{id}")
   public void updateUser(@PathVariable("id") Integer id, @RequestBody User user) {
        userRepository.update(id, user);
   }

    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        userRepository.delete(id);
    }

}
