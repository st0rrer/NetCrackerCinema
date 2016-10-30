package com.netcracker.cinema.model;

import javax.persistence.*;

@Entity
@Table(name = "dummies")
public class Dummy {
    @Id
    @Column(name = "DUMMY_ID")
    private long id;

    @Column(name = "DUMMY_NAME")
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
