package com.rb.web2.controllers;

import com.rb.web2.domain.user.User;
import com.rb.web2.services.CandidateApplicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/application")
public class CandidateApplicationController {
    
        @Autowired
        CandidateApplicationService service;
    
        @GetMapping
        public ResponseEntity<List<User>> getAllApplications() {
            List<User> applications = service.getAllApplications();
            return ResponseEntity.ok(applications);
        }
    
        @PostMapping
        public ResponseEntity<String> createApplication(@RequestBody @Validated User application) {
            try {
                service.create(application);
                return ResponseEntity.ok("Application created with ID: " + application.getId());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Error creating application: " + e.getMessage());
            }
        }
    
        @GetMapping("/{id}")
        public ResponseEntity<User> getApplication(@PathVariable String id) {
            User application = this.service.getApplicationById(id);
            if (application == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body(null);
            }
            return ResponseEntity.ok(application);
        }
    
        @PutMapping
        public ResponseEntity updateApplication(@RequestBody @Validated User application) {
            var updatedApplication = this.service.updateApplication(application);
            if (updatedApplication == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                    .body(null);
            }
            return ResponseEntity.ok(updatedApplication);
        }
    
        // @TODO Implement this method
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteApplication(@PathVariable String id) {
            return null;
        }

}