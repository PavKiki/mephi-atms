package ru.dsis.atms.users.dao.postgres;

public class PostgresRole {
    private Long id;
    private String name;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
