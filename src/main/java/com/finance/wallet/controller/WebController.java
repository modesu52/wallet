package com.finance.wallet.controller;

import com.finance.wallet.model.Transaction;
import com.finance.wallet.model.TransactionType;
import com.finance.wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/web")
public class WebController {

    @Autowired
    private TransactionService transactionService;

    // Главная страница со списком транзакций
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("transactions", transactionService.findAll());
        model.addAttribute("balance", transactionService.calculateBalance());
        model.addAttribute("newTransaction", new Transaction());
        return "index";
    }

    // Добавление новой транзакции через форму
    @PostMapping("/add")
    public String addTransaction(@ModelAttribute Transaction transaction) {
        transactionService.save(transaction);
        return "redirect:/web/";
    }

    // Удаление транзакции
    @GetMapping("/delete/{id}")
    public String deleteTransaction(@PathVariable Long id) {
        transactionService.deleteByID(id);
        return "redirect:/web/";
    }
}
