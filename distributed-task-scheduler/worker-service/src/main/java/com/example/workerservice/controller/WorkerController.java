package com.example.workerservice.controller;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WorkerController {

    private final RedisTemplate<String, String> redis;

    public WorkerController(RedisTemplate<String, String> redis) { this.redis = redis; }

    @GetMapping("/done")
    public List<String> done() {
        return redis.opsForList().range("task_done", 0, -1);
    }
}
