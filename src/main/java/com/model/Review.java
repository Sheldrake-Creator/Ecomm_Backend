package com.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference // Break the infinite recursion
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference // This breaks the infinite recursion
    private User user;

    private String review;

    @JsonIgnore
    private LocalDateTime createdAt;

}
