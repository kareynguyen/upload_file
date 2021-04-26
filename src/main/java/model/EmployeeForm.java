package model;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

public class EmployeeForm {
    private long id;
    private String name;
    private Date date;
    private MultipartFile avatar;
    private boolean gender;

    public EmployeeForm() {
    }

    public EmployeeForm(long id, String name, Date date, MultipartFile avatar, boolean gender) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.avatar = avatar;
        this.gender = gender;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(MultipartFile avatar) {
        this.avatar = avatar;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
