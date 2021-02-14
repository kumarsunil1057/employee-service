package org.example.vmware.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_id_gen")
    @SequenceGenerator(name = "task_id_gen")
    @Column(name = "id")
    private long id;

    @Column(name = "status", columnDefinition = "varchar(32) default 'CREATED'")
    @Enumerated(value = EnumType.STRING)
    private TaskStatus status;
}
