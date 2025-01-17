package ru.dsis.atms.client.tasks.dao;

import java.util.List;

public class TaskManagementProjectDao {
    private int id;
    private String title;
    private String state;
    private Director director;

    private static class Director {
        private int id;
        private String login;
        private String fio;
        private String picturePath;
        private String state;
        private List<String> systemRoles;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public String getFio() {
            return fio;
        }

        public void setFio(String fio) {
            this.fio = fio;
        }

        public String getPicturePath() {
            return picturePath;
        }

        public void setPicturePath(String picturePath) {
            this.picturePath = picturePath;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public List<String> getSystemRoles() {
            return systemRoles;
        }

        public void setSystemRoles(List<String> systemRoles) {
            this.systemRoles = systemRoles;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }
}
