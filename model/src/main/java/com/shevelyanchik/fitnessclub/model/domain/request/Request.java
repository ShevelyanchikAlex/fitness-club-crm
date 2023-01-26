package com.shevelyanchik.fitnessclub.model.domain.request;

import com.shevelyanchik.fitnessclub.model.domain.Service;
import com.shevelyanchik.fitnessclub.model.domain.Trainer;
import com.shevelyanchik.fitnessclub.model.domain.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "request")
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "createdDateTime")
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "trainingStartDateTime")
    private LocalDateTime trainingStartDateTime;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id", nullable = false)
    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)
    private Service service;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "requestStatus")
    private RequestStatus requestStatus;
}