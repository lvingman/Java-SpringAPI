package com.example.springapi.dto;

import com.example.springapi.api.models.User;

import java.util.List;

//This class registers the data in the JSON
public record UserMappingDTO(List<User> users) {
}
