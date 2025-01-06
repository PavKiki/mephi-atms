package ru.dsis.atms.users.dao;

public class PostgresRole {
    private Long id;
    private String name;

    public String getRoleName() {
        return name;
    }

    public void setRoleName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
