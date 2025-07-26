package com.pllis.mylog.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "BOOK_CATEGORY")
public class BookCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOK_CATEGORY_NO")
    private Integer bookCategoryNo;

    @Column(name="BOOK_CATEGORY_NAME", nullable = false, length = 100)
    private String bookCategoryName;

}
