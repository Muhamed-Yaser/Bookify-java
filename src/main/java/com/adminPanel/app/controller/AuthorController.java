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

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorDAO authorDAO;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // GET /authors
    @GetMapping
    public String listAuthors(Model model) {
        List<Author> authorList = authorDAO.findAll();
        model.addAttribute("authors", authorList);
        model.addAttribute("author", new Author());
        return "authorPage";
    }

    // POST /authors/add
    @PostMapping("/add")
    public String addAuthor(@Valid @ModelAttribute("author") Author author,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorDAO.findAll());
            return "authorPage";
        }
        authorDAO.save(author);
        return "redirect:/authors";
    }

    // delete /authors/{id}/delete
    @PostMapping("/{id}/delete")
    public String deleteAuthor(@PathVariable("id") int id) {
        authorDAO.delete(id);
        return "redirect:/authors";
    }
}