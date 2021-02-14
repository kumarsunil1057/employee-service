package org.example.vmware.controller;

import org.example.vmware.model.TaskStatus;
import org.example.vmware.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TaskController {
    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/task/{id}")
    public ResponseEntity<String> getStatus(@PathVariable("id") Long id){
        String message = "";
        TaskStatus status = taskService.getTaskStatus(id);
        if(status != TaskStatus.INVALID){
            message = "Task Status: "+status;
            return ResponseEntity.status(HttpStatus.OK).body(message);
        }
        message = "Task ID not Found";
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
    }

}
