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
@Table(name = "patron_management_table")
public class PatronManagementEntity {

    @Id
    @SequenceGenerator(
            name = "patron_management_sequence",
            sequenceName = "patron_management_sequence",
            allocationSize = 10
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "patron_management_sequence"
    )

    @Column(name = "PATRON_ID")
    private Long id;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @OneToMany(mappedBy = "PATRON", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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
