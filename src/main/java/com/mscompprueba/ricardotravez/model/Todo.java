package com.mscompprueba.ricardotravez.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "todo")
    private String todo;

    @Column(name = "completed")
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

}
