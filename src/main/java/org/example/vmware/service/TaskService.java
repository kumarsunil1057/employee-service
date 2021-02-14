package org.example.vmware.service;

import org.example.vmware.helper.CSVHelper;
import org.example.vmware.model.Employee;
import org.example.vmware.model.Task;
import org.example.vmware.model.TaskStatus;
import org.example.vmware.repository.EmployeeRepository;
import org.example.vmware.repository.TaskRepository;
import org.example.vmware.thread.ProcessTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TaskRepository taskRepository;

    public long createTask(MultipartFile file){
        Task t = new Task();
        t.setStatus(TaskStatus.CREATED);
        taskRepository.save(t);

        //TODO: async following code
        //FutureTask

        save(t.getId(), file);

        return t.getId();
    }

    public TaskStatus getTaskStatus(long id) {
        Task t = taskRepository.findById(id).orElse(null);
        if(t != null) {
            return t.getStatus();
        }
        return TaskStatus.INVALID;
    }

    public void save(long task_id, MultipartFile file) {
        Task t = taskRepository.findById(task_id).get();
        try {
            t.setStatus(TaskStatus.PROCCESSING);
            t = taskRepository.save(t);

            List<Employee> employees = CSVHelper.csvToEmployees(file.getInputStream());
            employeeRepository.saveAll(employees);

            t.setStatus(TaskStatus.SUCCESS);
            taskRepository.save(t);
        } catch (IOException e) {
            t.setStatus(TaskStatus.FAILURE);
            taskRepository.save(t);
            throw new RuntimeException("Failed to store csv data: " + e.getMessage());
        }
    }


}