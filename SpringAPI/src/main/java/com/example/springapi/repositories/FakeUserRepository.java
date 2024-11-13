package com.example.springapi.repositories;

import com.example.springapi.api.models.User;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class FakeUserRepository {

    private List<User> userList;


    @PostConstruct
    private void init(){
        userList = new ArrayList<>();

        User user1 = new User(1, "Ida", 32, "ida@mail.org");
        User user2 = new User(2, "Ray", 21, "ray@mail.org");
        User user3 = new User(3, "Jay", 22, "jay@mail.org");
        User user4 = new User(4, "Beat", 31, "beat@mail.org");
        User user5 = new User(5, "Bud", 25, "bud@mail.org");

        userList.addAll(Arrays.asList(user1, user2, user3, user4, user5));
    }


    //GET BY ID
    public Optional<User> getUser(Integer id){
        Optional<User> toReturn = Optional.empty();
        for(User user : userList){
            if(user.getId() == id){
                toReturn = Optional.of(user);
            }
        }
        return toReturn;
    }

    //GET ALL
    public Optional<List<User>> getUser(){
        Optional<List<User>> toReturn = Optional.empty();
        toReturn = Optional.of(userList);
        return toReturn;
    }

    //POST
    public void create(User user){
        userList.add(user);
    }


    //PUT
    public void update(Integer id, User user){
        Optional<User> toUpdate = getUser(id);
        if(toUpdate.isPresent()){
            userList.set(userList.indexOf(toUpdate.get()), user);
        }
    }


    //DELETE
    public void delete(Integer id){
        Optional<User> toDelete = getUser(id);
        if(toDelete.isPresent()){
            userList.remove(toDelete.get());
        }

    }


}
