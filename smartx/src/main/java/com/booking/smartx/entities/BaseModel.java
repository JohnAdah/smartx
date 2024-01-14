package com.booking.smartx.entities;

import jakarta.persistence.*;

@MappedSuperclass
public class BaseModel {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
