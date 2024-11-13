package com.example.springapi.dao.seeders;

import com.example.springapi.dao.repositories.UserRepository;
import com.example.springapi.dto.UserMappingDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class UserJsonDataLoader implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(UserJsonDataLoader.class);
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserJsonDataLoader(UserRepository userRepository, ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.count() == 0){
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/data/users.json")){
                UserMappingDTO allUsers = objectMapper.readValue(inputStream, UserMappingDTO.class);
                log.info("Reading {} runs from JSON data and saving to database.", allUsers.users().size());
                userRepository.saveAll(allUsers.users());
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data.");
            }
        } else {
            log.info("Database already loaded. Not loading startup data.");
        }
    }
}
