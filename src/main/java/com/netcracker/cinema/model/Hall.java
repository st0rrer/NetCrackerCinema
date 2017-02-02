package com.netcracker.cinema.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Hall implements Serializable {

    private long id;
    private String name;
}
