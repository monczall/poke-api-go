package com.pokeapigo.core.module.trainer;

import com.pokeapigo.core.module.trainer.util.enums.TrainerTeam;
import com.pokeapigo.core.role.RoleEntity;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "TRA_TRAINERS")
public class TrainerEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private UUID id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LEVEL")
    private Integer level;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "TEAM")
    private TrainerTeam team;

    @Column(name = "AVATARURL")
    private String avatarUrl;

    @Column(name = "FRIENDCODE")
    private String friendCode;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "TRA_ROLE_MEMBERS",
            joinColumns = @JoinColumn(name = "TRAINERID"),
            inverseJoinColumns = @JoinColumn(name = "ROLEID"))
    @Column(name = "ROLES")
    private Set<RoleEntity> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role ->
                new SimpleGrantedAuthority(role.getRole().name())
        ).toList();
    }

    public TrainerEntity() {
    }

    @Override
    public String getUsername() {
        return email;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getLevel() {
        return level;
    }

    public TrainerTeam getTeam() {
        return team;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getFriendCode() {
        return friendCode;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void setTeam(TrainerTeam team) {
        this.team = team;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }

    private TrainerEntity(TrainerEntityBuilder builder) {
        this.name = builder.name;
        this.level = builder.level;
        this.team = builder.team;
        this.avatarUrl = builder.avatarUrl;
        this.friendCode = builder.friendCode;
        this.email = builder.email;
        this.password = builder.password;
        this.roles = builder.roles;
    }

    public static class TrainerEntityBuilder {
        private String name;
        private Integer level;
        private TrainerTeam team;
        private String avatarUrl;
        private String friendCode;
        private String email;
        private String password;
        private Set<RoleEntity> roles;

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

        public TrainerEntityBuilder setRoles(Set<RoleEntity> roles) {
            this.roles = roles;
            return this;
        }

        public TrainerEntity build() {
            return new TrainerEntity(this);
        }

    }
}
