package com.maids.test.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book_management_table")
public class BookRecordsEntity {

    @Id
    @SequenceGenerator(
            name = "book_management_sequence",
            sequenceName = "book_management_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_management_sequence"
    )

    @Column(name = "BOOK_ID")
    private Long id;

    @Column(name = "BOOK_TITLE")
    private String bookTitle;

    @Column(name = "AUTHOR")
    private String author;

    @Column(name = "PUBLICATION_YEAR")
    private String yearPublished;

    @Column(name = "ISBN")
    private String isbn;

    @OneToMany(mappedBy = "BOOK", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BorrowRecordEntity> borrowingRecords;

    @Column(name = "DATE_CREATED")
    private String dateCreated;

    @Column(name = "TIME_CREATED")
    private String timeCreated;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "DATE_MODIFIED")
    private String dateModified;

    @Column(name = "TIME_MODIFIED")
    private String timeModified;

    @Column(name = "MODIFIED_BY")
    private String modifiedBy;

}
