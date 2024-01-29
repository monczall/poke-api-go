package com.pokeapigo.core.module.trainer;

import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;
import org.junit.jupiter.api.Test;

import static com.pokeapigo.core.module.trainer.util.factory.TrainerEntityFactory.getValidTrainerEntityAdmin;
import static com.pokeapigo.core.module.trainer.util.factory.TrainerEntityFactory.getValidTrainerEntityUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TrainerEntityTest {

    @Test
    void testTrainerBuilderAndGetters() {
        // given
        TrainerEntity trainerEntityUser = getValidTrainerEntityUser();
        TrainerEntity trainerEntityAdmin = getValidTrainerEntityAdmin();
        System.out.println(trainerEntityUser.getAuthorities());

        // when then
        assertEquals(1, trainerEntityUser.getAuthorities().size());
        assertEquals(2, trainerEntityAdmin.getAuthorities().size());
        assertTrue(trainerEntityUser.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER")));
        assertTrue(trainerEntityAdmin.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("USER")));
        assertTrue(trainerEntityAdmin.getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ADMIN")));
        assertEquals("UserName", trainerEntityUser.getName());
        assertEquals(35, trainerEntityUser.getLevel());
        assertEquals(TrainerTeam.MYSTIC, trainerEntityUser.getTeam());
        assertEquals("www.example.com/user", trainerEntityUser.getAvatarUrl());
        assertEquals("useruseruseruser", trainerEntityUser.getFriendCode());
        assertEquals("user@mail.com", trainerEntityUser.getUsername());
        assertEquals("$2a$10$o7kRRoeNaSAVUQhuhi8kiuvPBJXk4viGE18wmnGwnvB7vBGg9z.p6", trainerEntityUser.getPassword());
        assertTrue(trainerEntityUser.isAccountNonExpired());
        assertTrue(trainerEntityUser.isAccountNonLocked());
        assertTrue(trainerEntityUser.isCredentialsNonExpired());
        assertTrue(trainerEntityUser.isEnabled());
    }

}