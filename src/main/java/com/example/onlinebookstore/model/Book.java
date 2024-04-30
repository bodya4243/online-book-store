package com.example.onlinebookstore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@Setter
@SQLDelete(sql = "UPDATE book SET is_deleted = true WHERE id=?")
@SQLRestriction(value = "is_deleted = false")
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "author", nullable = false)
    private String author;
    
    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;
    
    @Column(name = "price", nullable = false)
    private BigDecimal price;
    
    private String description;
    private String coverImage;
    
    @Column(nullable = false)
    private boolean isDeleted = false;
}
