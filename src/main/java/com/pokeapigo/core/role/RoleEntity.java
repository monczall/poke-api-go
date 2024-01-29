package com.pokeapigo.core.role;

import com.pokeapigo.core.role.util.enums.TrainerRole;
import jakarta.persistence.*;

@Entity
@Table(name = "TRA_ROLES")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    private TrainerRole role;

    public RoleEntity(TrainerRole role) {
        this.role = role;
    }

    public TrainerRole getRole() {
        return role;
    }
}
