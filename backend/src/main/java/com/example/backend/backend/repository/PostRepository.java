package com.example.backend.backend.repository;


import com.example.backend.backend.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Integer>{
    List<Post> findByUserId(int userId);
    List<Post> findByApprovedTrue();
    Post findById(int id);
    Post deleteById(int id);
    long countByApprovedTrue();
    long countByApprovedFalse();

    @Query("SELECT p FROM Post p " + "WHERE (:city IS NULL OR p.location.address LIKE CONCAT('%', :city, '%')) " + "AND (:district IS NULL OR p.location.address LIKE CONCAT('%', :district, '%')) " + "AND (:ward IS NULL OR p.location.address LIKE CONCAT('%', :ward, '%')) " + "AND (:minPrice IS NULL OR p.price >= :minPrice) " + "AND (:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Post> searchPosts(@Param("city") String city, @Param("district") String district, @Param("ward") String ward, @Param("minPrice") BigDecimal minPrice, @Param("maxPrice") BigDecimal maxPrice);
}
