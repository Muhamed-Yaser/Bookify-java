package com.adminPanel.app.controller;

import com.adminPanel.app.dao.AuthorDAO;
import com.adminPanel.app.dao.BookDAO;
import com.adminPanel.app.dao.CategoryDAO;
import com.adminPanel.app.model.Author;
import com.adminPanel.app.model.Book;
import com.adminPanel.app.model.BookDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private AuthorDAO authorDAO;

    // Trims leading and trailing white spaces from all text inputs
    // Converts empty strings containing only spaces to null
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // 1. GET /books : Display all books
    @GetMapping
    public String listBooks(Model model) {
        List<Book> bookList = bookDAO.findAll();
        model.addAttribute("books", bookList);
        return "homePage";
    }

    // 2. GET /books/add : Show form to create a new book
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        Book book = new Book();
        book.setBookDetails(new BookDetails()); // Instantiate nested BookDetails object

        model.addAttribute("book", book);
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("authors", authorDAO.findAll());
        return "addBookFormPage";
    }

    // 3. POST /books/add : Validate and save the new book
    @PostMapping("/add")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult bindingResult,
                           Model model) {

        // Note: BindingResult must appear immediately after @Valid @ModelAttribute
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryDAO.findAll());
            model.addAttribute("authors", authorDAO.findAll());
            return "addBookFormPage"; // Reload form with validation error messages
        }

        // Maintain bidirectional relationship between Book and BookDetails
        if (book.getBookDetails() != null) {
            book.getBookDetails().setBook(book);
        }

        // Safely map selected Author IDs to persistent Author entities from the DB
        List<Author> selectedAuthors = new ArrayList<>();
        if (book.getAuthors() != null) {
            for (Author author : book.getAuthors()) {
                if (author != null && author.getId() != 0) {
                    Author dbAuthor = authorDAO.findById(author.getId());
                    if (dbAuthor != null) {
                        selectedAuthors.add(dbAuthor);
                    }
                }
            }
        }
        book.setAuthors(selectedAuthors);

        bookDAO.save(book);
        return "redirect:/books";
    }

    // 4. GET /books/{id} : View complete details of a specific book
    @GetMapping("/{id}")
    public String viewBookDetails(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.findById(id);
        model.addAttribute("book", book);
        return "viewMorePage";
    }

    // 5. GET /books/{id}/edit : Show form to update an existing book
    @GetMapping("/{id}/edit")
    public String showEditBookForm(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("authors", authorDAO.findAll());
        return "updateBookPage";
    }

    // 6. POST /books/{id}/update : Validate and update book details
    @PostMapping("/{id}/update")
    public String updateBook(@Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryDAO.findAll());
            model.addAttribute("authors", authorDAO.findAll());
            return "updateBookPage";
        }

        if (book.getBookDetails() != null) {
            book.getBookDetails().setBook(book);
        }

        List<Author> selectedAuthors = new ArrayList<>();
        if (book.getAuthors() != null) {
            for (Author author : book.getAuthors()) {
                if (author != null && author.getId() != 0) {
                    Author dbAuthor = authorDAO.findById(author.getId());
                    if (dbAuthor != null) {
                        selectedAuthors.add(dbAuthor);
                    }
                }
            }
        }
        book.setAuthors(selectedAuthors);

        bookDAO.update(book);
        return "redirect:/books";
    }

    // 7. POST /books/{id}/delete : Delete a book and its cascaded details
    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}