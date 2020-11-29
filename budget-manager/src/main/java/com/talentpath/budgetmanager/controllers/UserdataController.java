package com.talentpath.budgetmanager.controllers;

import com.talentpath.budgetmanager.daos.UserRepository;
import com.talentpath.budgetmanager.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/userdata")
public class UserdataController {

    @Autowired
    UserRepository repo;

    @GetMapping("/")
    public List<User> getAllUsers() {return repo.findAll();}

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Integer id) {return repo.getOne(id);}

    @PostMapping("/")
    public User addUser(@RequestBody User toAdd) { return repo.saveAndFlush(toAdd);}

    @PutMapping("/")
    public User editUser(@RequestBody User edited) {return repo.saveAndFlush(edited);}

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {repo.deleteById(id);}
}
