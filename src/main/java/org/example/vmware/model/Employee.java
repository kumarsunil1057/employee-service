package org.example.vmware.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_id_gen")
    @SequenceGenerator(name = "emp_id_gen")
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

