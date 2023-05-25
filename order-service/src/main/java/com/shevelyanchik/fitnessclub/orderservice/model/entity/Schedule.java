package com.shevelyanchik.fitnessclub.orderservice.model.entity;

import com.shevelyanchik.fitnessclub.orderservice.constant.ServiceType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "schedule")
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "training_start_date_time", nullable = false)
    private LocalDateTime trainingStartDateTime;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)
    private Service service;

    @Column(name = "trainer_id", nullable = false)
    private Long trainerId;

    @Column(name = "available_spots", nullable = false)
    private Long availableSpots;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "service_type", nullable = false)
    private ServiceType serviceType;

}
