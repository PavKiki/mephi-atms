package ru.dsis.atms.users.dao;

public class PostgresUser {
    private Long id;
    private String login;
    private String password;
    private Long roleId;

    public PostgresUser(Long id, String login, String password, Long roleId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.roleId = roleId;
    }

    public static PostgresUser of(String login, String password, Long roleId) {
        return new PostgresUser(null, login, password, roleId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
