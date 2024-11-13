package com.example.springapi.repositories;

import com.example.springapi.api.models.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


//This repository works with a dB stored in memory, not real dB
@Repository
public class UserRepository {

    private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
    private final JdbcClient jdbcClient;

    public UserRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Optional<List<User>> findAll() {
        List<User> users = jdbcClient.sql("SELECT * FROM `User`").query(User.class).list();
        return users.isEmpty() ? Optional.empty() : Optional.of(users);
    }

    public Optional<User> findById(Integer id){
        return jdbcClient.sql("SELECT * FROM `User` WHERE Id = :id")
                .param("id", id)
                .query(User.class)
                .optional();
    }

    public void create(User user) {
        var toCreate = jdbcClient.sql(
                "INSERT INTO `User` (Id, `Name`, Age, Email)" +
                "VALUES (?,?,?,?)")
                .params(user.getId(), user.getName(), user.getAge(), user.getEmail())
                .update();
        Assert.state(toCreate == 1, "Failed to create User" + user.getName());

    }

    public void update(Integer id, User user) {
        var toUpdate = jdbcClient.sql(
                "UPDATE `User` " +
                        "SET `Name` = ?, " +
                        "Age = ?, " +
                        "Email = ? " +
                        "WHERE Id = ?"
        ).params(user.getName(), user.getAge(), user.getEmail(), id)
        .update();

        Assert.state(toUpdate == 1, "Failed to update User: " + user.getName());
    }

    public void delete(Integer id) {
        var toDelete = jdbcClient.sql("DELETE FROM `User` WHERE Id = :id")
                .param("id", id)
                .update();

        Assert.state(toDelete == 1, "Failed to delete User: " + id);
    }




}
