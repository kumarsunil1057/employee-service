package org.example.vmware.controller;

import org.example.vmware.helper.CSVHelper;
import org.example.vmware.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employee")
public class FileController {

    @Autowired
    TaskService taskService;

    @GetMapping
    public String index() {
        return "Handling GET request!";
    }

    @PostMapping(consumes = {"text/csv", "multipart/form-data"})
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("action") String action) {
        if(!action.equals("upload")){
            ResponseEntity.status(HttpStatus.BAD_REQUEST);
        }
        String message = "";
        if (CSVHelper.hasCSVFormat(file)) {
            try {
                long task_id = taskService.createTask(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename() + "\n";
                message += "Task ID: " + task_id;
                return ResponseEntity.status(HttpStatus.OK).body(message);
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                message += "\nException: "+e.toString();
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

}
