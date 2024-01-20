package com.pokeapigo.core.module.trainer;

import com.pokeapigo.core.module.trainer.util.enums.TrainerRole;
import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static com.pokeapigo.core.common.utli.constants.DataBaseConstants.TRAINERS_TABLE;

@Entity
@Table(name = TRAINERS_TABLE)
public class TrainerEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "level")
    private Integer level;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "team")
    private TrainerTeam team;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "friend_code")
    private String friendCode;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private TrainerRole role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private TrainerEntity(TrainerEntityBuilder builder) {
        this.name = builder.name;
        this.level = builder.level;
        this.team = builder.team;
        this.avatarUrl = builder.avatarUrl;
        this.friendCode = builder.friendCode;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
    }

    public static class TrainerEntityBuilder {
        private String name;
        private Integer level;
        private TrainerTeam team;
        private String avatarUrl;
        private String friendCode;
        private String email;
        private String password;
        private TrainerRole role;

        public TrainerEntityBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public TrainerEntityBuilder setLevel(Integer level) {
            this.level = level;
            return this;
        }

        public TrainerEntityBuilder setTeam(TrainerTeam team) {
            this.team = team;
            return this;
        }

        public TrainerEntityBuilder setAvatarUrl(String avatarUrl) {
            this.avatarUrl = avatarUrl;
            return this;
        }

        public TrainerEntityBuilder setFriendCode(String friendCode) {
            this.friendCode = friendCode;
            return this;
        }

        public TrainerEntityBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public TrainerEntityBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public TrainerEntityBuilder setRole(TrainerRole role) {
            this.role = role;
            return this;
        }

        public TrainerEntity build() {
            return new TrainerEntity(this);
        }

    }
}
