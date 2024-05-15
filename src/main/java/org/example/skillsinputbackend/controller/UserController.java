package org.example.skillsinputbackend.controller;

import org.example.skillsinputbackend.model.User;
import org.example.skillsinputbackend.model.Skill;
import org.example.skillsinputbackend.repository.SkillRepository;
import org.example.skillsinputbackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SkillRepository skillRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        if (user.getSkills() != null && !user.getSkills().isEmpty()) {
            List<Skill> persistedSkills = new ArrayList<>();
            for (Skill skill : user.getSkills()) {
                Optional<Skill> existingSkillOptional = skillRepository.findBySkillname(skill.getSkillname());
                if (existingSkillOptional.isPresent()) {
                    persistedSkills.add(existingSkillOptional.get());
                } else {
                    Skill persistedSkill = skillRepository.save(skill);
                    persistedSkills.add(persistedSkill);
                }
            }
            user.setSkills(persistedSkills);
        }
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }
}
