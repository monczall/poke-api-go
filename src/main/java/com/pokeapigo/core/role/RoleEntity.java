package com.pokeapigo.core.role;

import com.pokeapigo.core.role.util.enums.TrainerRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "TRA_ROLES")
@Schema(description = "Object containing trainer role")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "NAME")
    @Schema(description = "Trainer Role, ex. 'USER', 'ADMIN'")
    private TrainerRole role;

    public RoleEntity() {
    }

    public RoleEntity(TrainerRole role) {
        this.role = role;
    }

    public TrainerRole getRole() {
        return role;
    }
}
