package com.shevelyanchik.fitnessclub.orderservice.model.entity;

import com.shevelyanchik.fitnessclub.orderservice.constant.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_date_time")
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    @Column(name = "training_start_date_time")
    private LocalDateTime trainingStartDateTime;

    @ManyToOne
    @JoinColumn(name = "service_id", referencedColumnName = "id", nullable = false)
    private Service service;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "trainer_id")
    private Long trainerId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus orderStatus;
}