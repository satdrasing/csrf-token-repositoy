package com.satendra.csrftokenrepository;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This controller has two methods one is get and one is post;
 * Get method is for to retrieve all response - in this scenario
 * csrf not intercepted but with post csrf intercepted.
 * csrf token repository for stateless authentication like basic and digest
 * login url authentication (native) csrf auto added.
 */
@RestController
public class TestController {
    List fruits = new ArrayList();

    {
        fruits.addAll(List.of("apple", "banana", "Grapes"));
    }

    @GetMapping("/test")
    public ResponseEntity<Collection<String>> getAllFruitsName() {
        return ResponseEntity.ok(fruits);
    }

    @PostMapping("/test")
    public ResponseEntity<Void> createFruits(@RequestBody FruitForm fruitForm) {
        fruits.add(fruitForm.getFruitName());
        return ResponseEntity.created(URI.create("/post" + fruitForm.getFruitName())).build();
    }
}
