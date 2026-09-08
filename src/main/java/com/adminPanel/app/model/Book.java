package com.adminPanel.app.model;

import javax.persistence.*;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor

public class Book{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToOne(mappedBy = "book" , cascade = CascadeType.ALL)

    private BookDetails bookDetails;

    @ManyToMany(cascade = {CascadeType.PERSIST , CascadeType.MERGE ,CascadeType.DETACH, CascadeType.REFRESH })

    @JoinTable(
            name = "book_author", joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))

    private List<Author> authors;

    public Book(String title) {
        this.title = title;
    }
}