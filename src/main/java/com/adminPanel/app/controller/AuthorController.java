package com.adminPanel.app.controller;

import com.adminPanel.app.dao.AuthorDAO;
import com.adminPanel.app.model.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private static final int PAGE_SIZE = 5;

    @Autowired
    private AuthorDAO authorDAO;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // GET /authors : list with pagination
    @GetMapping
    public String listAuthors(@RequestParam(defaultValue = "1") int page, Model model) {
        List<Author> authorList = authorDAO.findPage(page, PAGE_SIZE);
        long totalItems = authorDAO.countAll();
        int totalPages = (int) Math.ceil(totalItems / (double) PAGE_SIZE);
        if (totalPages == 0) totalPages = 1;

        model.addAttribute("authors", authorList);
        model.addAttribute("author", new Author());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "authorPage";
    }

    // POST /authors/add
    @PostMapping("/add")
    public String addAuthor(@Valid @ModelAttribute("author") Author author,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorDAO.findPage(1, PAGE_SIZE));
            long totalItems = authorDAO.countAll();
            int totalPages = (int) Math.ceil(totalItems / (double) PAGE_SIZE);
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", totalPages == 0 ? 1 : totalPages);
            return "authorPage";
        }
        authorDAO.save(author);
        return "redirect:/authors";
    }

    // GET /authors/{id}/edit : Show form to update an existing author
    @GetMapping("/{id}/edit")
    public String showEditAuthorForm(@PathVariable("id") int id, Model model) {
        Author author = authorDAO.findById(id);
        model.addAttribute("author", author);
        return "updateAuthorPage";
    }

    // POST /authors/{id}/update
    @PostMapping("/{id}/update")
    public String updateAuthor(@PathVariable("id") int id,
                               @Valid @ModelAttribute("author") Author author,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "updateAuthorPage";
        }
        author.setId(id);
        authorDAO.update(author);
        return "redirect:/authors";
    }

    // POST /authors/{id}/delete
    @PostMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (authorDAO.hasBooks(id)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Cannot delete this author: they still have books linked to them. Remove those books or their authorship first.");
        } else {
            authorDAO.delete(id);
        }
        return "redirect:/authors";
    }
}
