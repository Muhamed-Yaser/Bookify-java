package com.adminPanel.app.controller;

import com.adminPanel.app.dao.CategoryDAO;
import com.adminPanel.app.model.Category;
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
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryDAO categoryDAO;

    // remove white spaces
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // GET categories
    @GetMapping
    public String listCategories(Model model) {
        List<Category> categoryList = categoryDAO.findAll();
        model.addAttribute("categories", categoryList);
        model.addAttribute("category", new Category());
        return "categoryPage";
    }

    // POST /categories/add
    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryDAO.findAll());
            return "categoryPage";
        }
        categoryDAO.save(category);
        return "redirect:/categories";
    }
}