package com.example.schedulerservice.controller;

import com.example.schedulerservice.model.Task;
import com.example.schedulerservice.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService) { this.taskService = taskService; }

    @PostMapping("/tasks")
    public ResponseEntity<Map<String,String>> submitTask(@RequestBody Task t) {
        String id = taskService.submitTask(t.getPayload(), t.getDelaySeconds());
        return ResponseEntity.ok(Map.of("taskId", id));
    }

    @GetMapping("/tasks")
    public ResponseEntity<Collection<Task>> allTasks() {
        return ResponseEntity.ok(taskService.listTasks().values());
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTask(@PathVariable String id) {
        Task t = taskService.getTask(id);
        if (t==null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(t);
    }
}
