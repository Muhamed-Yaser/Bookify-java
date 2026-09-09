package com.adminPanel.app.controller;

import com.adminPanel.app.dao.AuthorDAO;
import com.adminPanel.app.dao.BookDAO;
import com.adminPanel.app.dao.CategoryDAO;
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

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // GET /books
    @GetMapping
    public String listBooks(Model model) {
        List<Book> bookList = bookDAO.findAll();
        model.addAttribute("books", bookList);
        return "homePage";
    }

    // GET /books/add
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        Book book = new Book();
        book.setBookDetails(new BookDetails());

        model.addAttribute("book", book);
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("authors", authorDAO.findAll());
        return "addBookFormPage";
    }

    // POST /books/add
    @PostMapping("/add")
    public String saveBook(@Valid @ModelAttribute("book") Book book,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryDAO.findAll());
            model.addAttribute("authors", authorDAO.findAll());
            return "addBookFormPage"; // العودة لصفحة النموذج لعرض أخطاء التحقق
        }

        // book and book details
        if (book.getBookDetails() != null) {
            book.getBookDetails().setBook(book);
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    // GET /books/{id}
    @GetMapping("/{id}")
    public String viewBookDetails(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.findById(id);
        model.addAttribute("book", book);
        return "viewMorePage";
    }

    // GET /books/{id}/edit
    @GetMapping("/{id}/edit")
    public String showEditBookForm(@PathVariable("id") int id, Model model) {
        Book book = bookDAO.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("categories", categoryDAO.findAll());
        model.addAttribute("authors", authorDAO.findAll());
        return "updateBookPage";
    }

    // update /books/{id}/update
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

        bookDAO.update(book);
        return "redirect:/books";
    }

    // delete /books/{id}/delete
    @PostMapping("/{id}/delete")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect:/books";
    }
}