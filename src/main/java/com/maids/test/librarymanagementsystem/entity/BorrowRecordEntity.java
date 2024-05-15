package com.maids.test.librarymanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "borrowing_records_table")
public class BorrowRecordEntity {

    @Id
    @SequenceGenerator(
            name = "borrowing_records_sequence",
            sequenceName = "borrowing_records_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "borrowing_records_sequence"
    )

    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOOK")
    private BookRecordsEntity BOOK;

    @ManyToOne
    @JoinColumn(name = "PATRON")
    private PatronManagementEntity PATRON;

    @Column(name = "BORROW_ID")
    private String borrowId;

    @Column(name = "DATE_COLLECTED")
    private String dateCollected;

    @Column(name = "TIME_COLLECTED")
    private String timeCollected;

    @Column(name = "DATE_RETURNED")
    private String dateReturned;

    @Column(name = "TIME_RETURNED")
    private String timeReturned;

    @Column(name = "STATUS")
    private String status;
}
