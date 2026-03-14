package com.solncev.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(
            name = "user role",
            joinColumns = @JoinColumn(name="user id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="role id", referencedColumnName = "id")
    )
    private List<Role> roles;

    @ManyToMany(mappedBy = "/roles")
    private List<User> users;

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<User> getUsers() {
        return users;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
