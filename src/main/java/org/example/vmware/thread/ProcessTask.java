package org.example.vmware.thread;

import lombok.NoArgsConstructor;
import org.example.vmware.helper.CSVHelper;
import org.example.vmware.model.Employee;
import org.example.vmware.model.Task;
import org.example.vmware.model.TaskStatus;
import org.example.vmware.repository.EmployeeRepository;
import org.example.vmware.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@NoArgsConstructor
public class ProcessTask {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TaskRepository taskRepository;

    public void save(Task t, MultipartFile file) {
        try {
            System.out.println("process execution started");
            //Thread.sleep(10000);
            t.setStatus(TaskStatus.PROCCESSING);
            t = taskRepository.save(t);

            List<Employee> employees = CSVHelper.csvToEmployees(file.getInputStream());
            employeeRepository.saveAll(employees);

            t.setStatus(TaskStatus.SUCCESS);
            taskRepository.save(t);

            System.out.println("process execution ended");
        } catch (IOException e) {
            t.setStatus(TaskStatus.FAILURE);
            taskRepository.save(t);
            throw new RuntimeException("Failed to store csv data: " + e.getMessage());
        }
    }
}
