package com.adminPanel.app.model;

import javax.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

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

    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "publication_date")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "number_of_pages")
    private int numberOfPages;

    @Column(name = "language")
    private String language;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private Book book;

    public BookDetails(String isbn, Date publicationDate, String publisher, int numberOfPages, String language) {
        this.isbn = isbn;
        this.publicationDate = publicationDate;
        this.publisher = publisher;
        this.numberOfPages = numberOfPages;
        this.language = language;
    }
}