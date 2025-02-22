package com.bridgelabz.greeting_app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/greeting")
public class GreetingController {

    @GetMapping
    public ResponseEntity<Map<String, String>> getGreeting() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from GET");
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> postGreeting(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from POST");
        response.put("received", request.toString());
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Map<String, String>> putGreeting(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from PUT");
        response.put("updated", request.toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteGreeting() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Hello from DELETE");
        return ResponseEntity.ok(response);
    }

    private final GreetingService greetingService;

    @Autowired
    public GreetingController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> getGreeting(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName) {

        Map<String, String> response = new HashMap<>();
        response.put("message", greetingService.getGreetingMessage(firstName, lastName));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getGreetingById(@PathVariable Long id) {
        Optional<Greeting> greeting = greetingService.getGreetingById(id);

        if (greeting.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", greeting.get().getMessage());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/all")
    public ResponseEntity<List<Map<String, String>>> getAllGreetings() {
        List<Greeting> greetings = greetingService.getAllGreetings();

        List<Map<String, String>> response = greetings.stream()
                .map(greeting -> {
                    Map<String, String> map = new HashMap<>();
                    map.put("id", greeting.getId().toString());
                    map.put("message", greeting.getMessage());
                    return map;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateGreeting(
            @PathVariable Long id,
            @RequestParam String newMessage) {

        Optional<Greeting> updatedGreeting = greetingService.updateGreeting(id, newMessage);

        if (updatedGreeting.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Greeting updated successfully!");
            response.put("updatedMessage", updatedGreeting.get().getMessage());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteGreeting(@PathVariable Long id) {
        boolean deleted = greetingService.deleteGreeting(id);

        if (deleted) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Greeting deleted successfully!");
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
