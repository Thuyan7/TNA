package com.example.backend.backend.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table (name ="users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    private String fullName;
    private String email;
    private String phone;
    private String password;


    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @ManyToOne
    @JoinColumn (name = "role_id")
    private Role role;

    @OneToMany (mappedBy = "user")
    private Set<Post> listPost;

    @OneToMany (mappedBy = "user")
    private Set<Comment> listComment;

    public User( String email, String password, String phone) {
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    @PrePersist
    protected void onCreate() {
        created = new Date();
        updated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();

    }

    public User() {
        super();
    }

    public Set<Comment> getListComment() {
        return listComment;
    }

    public void setListReview(Set<Comment> listComment) {
        this.listComment = listComment;
    }

    public Set<Post> getListPost() {
        return listPost;
    }

    public void setListPost(Set<Post> listPost) {
        this.listPost = listPost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
