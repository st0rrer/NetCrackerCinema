package com.netcracker.cinema.domains;

import com.sun.istack.internal.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dummies")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Dummy {
    @Id
    @Column(name = "DUMMY_ID")
    private long id;

    @Column(name = "DUMMY_NAME")
    private String name;
}
