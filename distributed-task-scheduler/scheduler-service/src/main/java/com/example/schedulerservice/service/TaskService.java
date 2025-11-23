package com.example.schedulerservice.service;

import com.example.schedulerservice.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class TaskService {

    private final RedisTemplate<String, String> redis;
    private final ObjectMapper mapper;
    private final Map<String, Task> tasks = new ConcurrentHashMap<>();

    public TaskService(RedisTemplate<String, String> redis, ObjectMapper mapper) {
        this.redis = redis; this.mapper = mapper;
    }

    public String submitTask(String payload, int delaySeconds) {
        String id = UUID.randomUUID().toString();
        Task t = new Task(id, payload, System.currentTimeMillis(), delaySeconds);
        tasks.put(id, t);
        try {
            String json = mapper.writeValueAsString(t);
            // Push to Redis list 'task_queue'
            redis.opsForList().rightPush("task_queue", json);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public Map<String, Task> listTasks() { return tasks; }
    public Task getTask(String id) { return tasks.get(id); }
    public void updateTask(Task t) { tasks.put(t.getId(), t); }
}
