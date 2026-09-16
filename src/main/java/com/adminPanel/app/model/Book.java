package com.adminPanel.app.model;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull(message = "Title is required")
    @Size(min = 1, message = "Title is required")
    @Column(name = "title")
    private String title;

    @Valid
    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private BookDetails bookDetails;

    // Requirement: Category must be selected
    @NotNull(message = "Category must be selected")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    // Requirement: At least one author must be selected
    @NotNull(message = "At least one author must be selected")
    @Size(min = 1, message = "At least one author must be selected")
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;

    public Book(String title) {
        this.title = title;
    }
}