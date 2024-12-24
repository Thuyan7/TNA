package com.example.backend.backend.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column (name = "title")
    private String title;

    @Column (name = "price")
    private double price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Column(name = "approved", nullable = false)
    private boolean approved = false;

    @ManyToOne
    @JoinColumn (name = "user_id")
    private User user;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn (name = "location_id")
    private Location location;

    @OneToMany (mappedBy = "post",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<Comment> listComment;

    @OneToMany(mappedBy = "post", cascade = CascadeType.PERSIST, orphanRemoval = false)
    private Set<Image> listImages = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "post_amenities",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "amenity_id")
    )
    private Set<Amenity> listAmenities = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        created = new Date();
        updated = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = new Date();

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Set<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(Set<Comment> listComment) {
        this.listComment = listComment;
    }

    public Set<Image> getListImages() {
        return listImages;
    }

    public void setListImages(Set<Image> listImages) {
        if (listImages != null) {
            this.listImages.clear();
            this.listImages.addAll(listImages);
        }
    }

    public Set<Amenity> getListAmenities() {
        return listAmenities;
    }

    public void setListAmenities(Set<Amenity> listAmenities) {
        this.listAmenities = listAmenities;
    }
}
