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

    private static final int PAGE_SIZE = 5;

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private AuthorDAO authorDAO;

    // Trims extra spaces from text inputs
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // Small helper method: takes a list of author id numbers and returns
    // the matching Author objects fetched from the database.
    // This is the simple way we use to turn the selected checkboxes/options
    // in the form into real Author objects before saving a Book.
    private List<Author> getAuthorsFromIds(List<Integer> authorIds) {
        List<Author> result = new ArrayList<>();

        if (authorIds == null) {
            return result;
        }

        for (Integer authorId : authorIds) {
            Author author = authorDAO.findById(authorId);
            if (author != null) {
                result.add(author);
            }
        }

        return result;
    }

    // 1. GET /books : Display all books, with optional search (by title or ISBN) and pagination
    @GetMapping
    public String listBooks(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(required = false) String search,
                            Model model) {
        List<Book> bookList;
        long totalItems;

        if (search != null && !search.trim().isEmpty()) {
            bookList = bookDAO.search(search.trim(), page, PAGE_SIZE);
            totalItems = bookDAO.countSearch(search.trim());
            model.addAttribute("search", search.trim());
        } else {
            bookList = bookDAO.findPage(page, PAGE_SIZE);
            totalItems = bookDAO.countAll();
        }

        int totalPages = (int) Math.ceil(totalItems / (double) PAGE_SIZE);
        if (totalPages == 0) {
            totalPages = 1;
        }

        model.addAttribute("books", bookList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "homePage";
    }

    // 2. GET /books/add : Show form to create a new book
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        Book book = new Book();
        book.setBookDetails(new BookDetails());

        model.addAttribute("book", book);
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("authors", authorDAO.findAll());
        model.addAttribute("selectedAuthorIds", new ArrayList<Integer>()); // nothing selected yet
        return "addBookFormPage";
    }

    // 3. POST /books/add : Validate and save the new book
    @PostMapping("/add")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult bindingResult,
                           @RequestParam(value = "authorIds", required = false) List<Integer> authorIds,
                           Model model) {

        // Get the real Author objects from the database using the selected ids
        List<Author> selectedAuthors = getAuthorsFromIds(authorIds);
        book.setAuthors(selectedAuthors);

        // At least one author must be chosen
        if (selectedAuthors.isEmpty()) {
            bindingResult.rejectValue("authors", "required", "Please select at least one author.");
        }

        // ==========================================
        // NEW: Category validation check
        // ==========================================
        if (book.getCategory() == null || book.getCategory().getId() == 0) {
            bindingResult.rejectValue("category", "required", "Please select a category.");
        }

        // Check that the ISBN is not already used by another book
        if (book.getBookDetails() != null && book.getBookDetails().getIsbn() != null) {
            BookDetails existing = bookDAO.findBookDetailsByIsbn(book.getBookDetails().getIsbn());
            if (existing != null) {
                bindingResult.rejectValue("bookDetails.isbn", "duplicate", "This ISBN is already used by another book.");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryDAO.findAll());
            model.addAttribute("authors", authorDAO.findAll());
            model.addAttribute("selectedAuthorIds", authorIds == null ? new ArrayList<Integer>() : authorIds);
            return "addBookFormPage";
        }

        // Keep the two-way link between Book and BookDetails
        if (book.getBookDetails() != null) {
            book.getBookDetails().setBook(book);
        }

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

        // Build the list of currently selected author ids, so the form
        // can show which authors are already chosen for this book.
        List<Integer> selectedAuthorIds = new ArrayList<>();
        if (book.getAuthors() != null) {
            for (Author author : book.getAuthors()) {
                selectedAuthorIds.add(author.getId());
            }
        }

        model.addAttribute("book", book);
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("authors", authorDAO.findAll());
        model.addAttribute("selectedAuthorIds", selectedAuthorIds);
        return "updateBookPage";
    }

    // 6. POST /books/{id}/update : Validate and update book details
    @PostMapping("/{id}/update")
    public String updateBook(@PathVariable("id") int id,
                             @Valid @ModelAttribute("book") Book book,
                             BindingResult bindingResult,
                             @RequestParam(value = "authorIds", required = false) List<Integer> authorIds,
                             Model model) {

        List<Author> selectedAuthors = getAuthorsFromIds(authorIds);
        book.setAuthors(selectedAuthors);

        if (selectedAuthors.isEmpty()) {
            bindingResult.rejectValue("authors", "required", "Please select at least one author.");
        }

        // Check ISBN uniqueness, but allow the book to keep its own ISBN
        if (book.getBookDetails() != null && book.getBookDetails().getIsbn() != null) {
            BookDetails existing = bookDAO.findBookDetailsByIsbn(book.getBookDetails().getIsbn());
            if (existing != null && existing.getBook() != null && existing.getBook().getId() != id) {
                bindingResult.rejectValue("bookDetails.isbn", "duplicate", "This ISBN is already used by another book.");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryDAO.findAll());
            model.addAttribute("authors", authorDAO.findAll());
            model.addAttribute("selectedAuthorIds", authorIds == null ? new ArrayList<Integer>() : authorIds);
            return "updateBookPage";
        }

        book.setId(id);
        if (book.getBookDetails() != null) {
            book.getBookDetails().setBook(book);
        }

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