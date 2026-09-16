package com.adminPanel.app.controller;

import com.adminPanel.app.dao.AuthorDAO;
import com.adminPanel.app.dao.BookDAO;
import com.adminPanel.app.model.Author;
import com.adminPanel.app.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

// This controller handles the "Book Authors" page.
// On this page the user picks one book, and then can move authors
// between "Available Authors" and "Selected Authors" for that book.
@Controller
@RequestMapping("/book-authors")
public class BookAuthorController {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private AuthorDAO authorDAO;

    // GET /book-authors : shows the page.
    // If a bookId is given in the URL, we load that book.
    // If not, we just show the first book in the list (if any exist).
    @GetMapping
    public String showPage(@RequestParam(value = "bookId", required = false) Integer bookId, Model model) {

        List<Book> allBooks = bookDAO.findAll();
        model.addAttribute("allBooks", allBooks);

        // Find which book is selected
        Book selectedBook = null;
        if (bookId != null) {
            selectedBook = bookDAO.findById(bookId);
        } else if (!allBooks.isEmpty()) {
            // We use findById here (not the book object from allBooks directly),
            // because findById is the version that also loads the authors list.
            int firstBookId = allBooks.get(0).getId();
            selectedBook = bookDAO.findById(firstBookId);
        }
        model.addAttribute("selectedBook", selectedBook);

        // Build the two lists: authors already on this book (selected)
        // and all other authors (available)
        List<Author> selectedAuthors = new ArrayList<>();
        if (selectedBook != null && selectedBook.getAuthors() != null) {
            selectedAuthors.addAll(selectedBook.getAuthors());
        }

        List<Author> availableAuthors = new ArrayList<>();
        List<Author> allAuthors = authorDAO.findAll();

        for (Author author : allAuthors) {
            boolean alreadySelected = false;

            for (Author selected : selectedAuthors) {
                if (selected.getId().equals(author.getId())) {
                    alreadySelected = true;
                    break;
                }
            }

            if (!alreadySelected) {
                availableAuthors.add(author);
            }
        }

        model.addAttribute("selectedAuthors", selectedAuthors);
        model.addAttribute("availableAuthors", availableAuthors);

        return "bookAuthorsPage";
    }

    // POST /book-authors/save : saves the final list of authors for the chosen book.
    @PostMapping("/save")
    public String saveBookAuthors(@RequestParam("bookId") int bookId,
                                  @RequestParam(value = "authorIds", required = false) List<Integer> authorIds,
                                  Model model,
                                  RedirectAttributes redirectAttributes) {

        Book book = bookDAO.findById(bookId);

        List<Author> newAuthorList = new ArrayList<>();
        if (authorIds != null) {
            for (Integer authorId : authorIds) {
                Author author = authorDAO.findById(authorId);
                if (author != null) {
                    newAuthorList.add(author);
                }
            }
        }

        book.setAuthors(newAuthorList);
        bookDAO.update(book);

        // Small message shown on the page after the redirect, to tell
        // the user that the save actually happened.
        redirectAttributes.addFlashAttribute("successMessage", "Authors updated successfully for \"" + book.getTitle() + "\".");

        return "redirect:/book-authors?bookId=" + bookId;
    }
}