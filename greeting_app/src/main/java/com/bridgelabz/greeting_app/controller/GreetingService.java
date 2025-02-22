package com.bridgelabz.greeting_app.controller;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GreetingService {
    private final GreetingRepository greetingRepository;

    public String getGreetingMessage() {
        return "Hello World";
    }
    public GreetingService(GreetingRepository greetingRepository) {
        this.greetingRepository = greetingRepository;
    }
    public String getGreetingMessage(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            return "Hello, " + firstName + " " + lastName + "!";
        } else if (firstName != null) {
            return "Hello, " + firstName + "!";
        } else if (lastName != null) {
            return "Hello, Mr./Ms. " + lastName + "!";
        }
        greetingRepository.save(new Greeting());
        return "Hello World!";
    }
    public String getGreetingMsg(String firstName, String lastName) {
        String message;

        if (firstName != null && lastName != null) {
            message = "Hello, " + firstName + " " + lastName + "!";
        } else if (firstName != null) {
            message = "Hello, " + firstName + "!";
        } else if (lastName != null) {
            message = "Hello, Mr./Ms. " + lastName + "!";
        } else {
            message = "Hello World!";
        }

        // Save greeting to the database
        Greeting savedGreeting = greetingRepository.save(new Greeting(message));
        return "Greeting saved with ID: " + savedGreeting.getId();
    }
    public Optional<Greeting> getGreetingById(Long id) {
        return greetingRepository.findById(id);
    }
    public List<Greeting> getAllGreetings() {
        return greetingRepository.findAll();
    }
    public Optional<Greeting> updateGreeting(Long id, String newMessage) {
        Optional<Greeting> existingGreeting = greetingRepository.findById(id);

        if (existingGreeting.isPresent()) {
            Greeting greeting = existingGreeting.get();
            greeting.setMessage(newMessage);
            return Optional.of(greetingRepository.save(greeting));
        }

        return Optional.empty();
    }
}
