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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private static final int PAGE_SIZE = 5;

    @Autowired
    private CategoryDAO categoryDAO;

    // remove white spaces
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    // GET /categories : list with pagination
    @GetMapping
    public String listCategories(@RequestParam(defaultValue = "1") int page, Model model) {
        List<Category> categoryList = categoryDAO.findPage(page, PAGE_SIZE);
        long totalItems = categoryDAO.countAll();
        int totalPages = (int) Math.ceil(totalItems / (double) PAGE_SIZE);
        if (totalPages == 0) totalPages = 1;

        model.addAttribute("categories", categoryList);
        model.addAttribute("category", new Category());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        return "categoryPage";
    }

    // POST /categories/add
    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") Category category,
                              BindingResult bindingResult,
                              Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryDAO.findPage(1, PAGE_SIZE));
            long totalItems = categoryDAO.countAll();
            int totalPages = (int) Math.ceil(totalItems / (double) PAGE_SIZE);
            model.addAttribute("currentPage", 1);
            model.addAttribute("totalPages", totalPages == 0 ? 1 : totalPages);
            return "categoryPage";
        }
        categoryDAO.save(category);
        return "redirect:/categories";
    }

    // GET /categories/{id}/edit : Show form to update an existing category
    @GetMapping("/{id}/edit")
    public String showEditCategoryForm(@PathVariable("id") int id, Model model) {
        Category category = categoryDAO.findById(id);
        model.addAttribute("category", category);
        return "updateCategoryPage";
    }

    // POST /categories/{id}/update
    @PostMapping("/{id}/update")
    public String updateCategory(@PathVariable("id") int id,
                                 @Valid @ModelAttribute("category") Category category,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            return "updateCategoryPage";
        }
        category.setId(id);
        categoryDAO.update(category);
        return "redirect:/categories";
    }

    // POST /categories/{id}/delete
    @PostMapping("/{id}/delete")
    public String deleteCategory(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        if (categoryDAO.hasBooks(id)) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Cannot delete this category: it still has books linked to it. Reassign or delete those books first.");
        } else {
            categoryDAO.delete(id);
        }
        return "redirect:/categories";
    }
}
