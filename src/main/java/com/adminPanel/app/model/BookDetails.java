package com.adminPanel.app.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "book_details")
@Getter
@Setter
@NoArgsConstructor
public class BookDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "ISBN is required")
    @Size(min = 1, message = "ISBN is required")
    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "publication_date")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publicationDate;

    // Requirement: Publisher is required
    @NotNull(message = "Publisher is required")
    @Size(min = 1, message = "Publisher is required")
    @Column(name = "publisher")
    private String publisher;

    @Min(value = 1, message = "Number of pages must be greater than 0")
    @Column(name = "number_of_pages")
    private int numberOfPages;

    @Column(name = "language")
    private String language;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;
}