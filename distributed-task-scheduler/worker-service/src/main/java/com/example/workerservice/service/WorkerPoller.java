package com.example.workerservice.service;

import com.example.workerservice.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class WorkerPoller {

    private final RedisTemplate<String, String> redis;
    private final ObjectMapper mapper;

    public WorkerPoller(RedisTemplate<String, String> redis, ObjectMapper mapper) {
        this.redis = redis; this.mapper = mapper;
    }

    // poll every 2 seconds
    @Scheduled(fixedDelay = 2000)
    public void poll() {
        try {
            String json = redis.opsForList().leftPop("task_queue");
            if (json == null) return;
            Task t = mapper.readValue(json, Task.class);
            System.out.println("Processing task: " + t.getId() + " payload=" + t.getPayload());
            // simulate processing time
            Thread.sleep(Math.max(500, t.getDelaySeconds() * 1000L));
            // On success, write to 'task_done' list
            t.setPayload(t.getPayload() + " [processed at " + Instant.now().toString() + "]");
            redis.opsForList().rightPush("task_done", mapper.writeValueAsString(t));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
