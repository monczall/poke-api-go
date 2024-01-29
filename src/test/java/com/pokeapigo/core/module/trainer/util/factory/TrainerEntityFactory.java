package com.pokeapigo.core.module.trainer.util.factory;

import com.pokeapigo.core.module.trainer.TrainerEntity;
import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;
import com.pokeapigo.core.role.RoleEntity;
import com.pokeapigo.core.role.util.enums.TrainerRole;

import java.util.Set;

public class TrainerEntityFactory {

    public static TrainerEntity getValidTrainerEntityUser() {
        return new TrainerEntity.TrainerEntityBuilder()
                .setName("UserName")
                .setLevel(35)
                .setTeam(TrainerTeam.MYSTIC)
                .setAvatarUrl("www.example.com/user")
                .setFriendCode("useruseruseruser")
                .setEmail("user@mail.com")
                .setPassword("$2a$10$o7kRRoeNaSAVUQhuhi8kiuvPBJXk4viGE18wmnGwnvB7vBGg9z.p6")
                .setRoles(Set.of(new RoleEntity(TrainerRole.USER))
                ).build();
    }

    public static TrainerEntity getValidTrainerEntityAdmin() {
        return new TrainerEntity.TrainerEntityBuilder()
                .setName("AdminName")
                .setLevel(50)
                .setTeam(TrainerTeam.VALOR)
                .setAvatarUrl("www.example.com/admin")
                .setFriendCode("adminadminadmina")
                .setEmail("admin@mail.com")
                .setPassword("$2a$10$o7kRRoeNaSAVUQhuhi8kiuvPBJXk4viGE18wmnGwnvB7vBGg9z.p6")
                .setRoles(Set.of(new RoleEntity(TrainerRole.USER), new RoleEntity(TrainerRole.ADMIN))
                ).build();
    }
}
