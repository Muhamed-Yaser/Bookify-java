package com.adminPanel.app.controller;

import com.adminPanel.app.dao.AuthorDAO;
import com.adminPanel.app.dao.BookDAO;
import com.adminPanel.app.dao.CategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private BookDAO bookDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private AuthorDAO authorDAO;

    @GetMapping("/")
    public String showDashboard(Model model) {
        // Fetch counts for our dashboard statistics
        long totalBooks = bookDAO.countAll();

        // Using .size() on findAll() is a simple way to get counts for smaller tables
        long totalCategories = categoryDAO.findAll().size();
        long totalAuthors = authorDAO.findAll().size();

        // Pass the data to the view
        model.addAttribute("totalBooks", totalBooks);
        model.addAttribute("totalCategories", totalCategories);
        model.addAttribute("totalAuthors", totalAuthors);

        return "dashboard"; // This refers to dashboard.jsp
    }
}