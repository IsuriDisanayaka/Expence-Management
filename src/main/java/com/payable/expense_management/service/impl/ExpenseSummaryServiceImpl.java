package com.payable.expense_management.service.impl;

import com.payable.expense_management.dto.ExpenseSummaryDTO;
import com.payable.expense_management.entity.Category;
import com.payable.expense_management.entity.Expense;
import com.payable.expense_management.repo.ExpenseRepository;
import com.payable.expense_management.service.ExpenseSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExpenseSummaryServiceImpl implements ExpenseSummaryService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public ExpenseSummaryDTO getExpenseSummary() {
        List<Expense> expenses = expenseRepository.findAll();
        return calculateSummary(expenses);
    }

    @Override
    public ExpenseSummaryDTO getExpenseSummaryByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Expense> allExpenses = expenseRepository.findAll();

        List<Expense> filteredExpenses = allExpenses.stream()
                .filter(expense -> !expense.getDate().isBefore(startDate) && !expense.getDate().isAfter(endDate))
                .collect(Collectors.toList());

        return calculateSummary(filteredExpenses);
    }

    private ExpenseSummaryDTO calculateSummary(List<Expense> expenses) {
        // Initialize map with zero for all categories
        Map<Category, BigDecimal> totalPerCategory = new EnumMap<>(Category.class);
        for (Category category : Category.values()) {
            totalPerCategory.put(category, BigDecimal.ZERO);
        }

        // Calculate total per category
        for (Expense expense : expenses) {
            Category category = expense.getCategory();
            BigDecimal currentTotal = totalPerCategory.get(category);
            totalPerCategory.put(category, currentTotal.add(expense.getAmount()));
        }

        // Find highest expense category
        Category highestCategory = null;
        BigDecimal highestAmount = BigDecimal.ZERO;

        for (Map.Entry<Category, BigDecimal> entry : totalPerCategory.entrySet()) {
            if (entry.getValue().compareTo(highestAmount) > 0) {
                highestAmount = entry.getValue();
                highestCategory = entry.getKey();
            }
        }

        return new ExpenseSummaryDTO(totalPerCategory, highestCategory);
    }
}