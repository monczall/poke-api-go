package com.pokeapigo.core.role;

import jakarta.persistence.*;

@Entity
@Table(name = "TRA_ROLES")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    public String getName() {
        return name;
    }
}
