package com.payable.expense_management.service.impl;

import com.payable.expense_management.dto.ExpenseDTO;
import com.payable.expense_management.entity.Expense;
import com.payable.expense_management.repo.ExpenseRepository;
import com.payable.expense_management.service.ExpenseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void createExpense(ExpenseDTO expenseDTO) {
        Expense expense = modelMapper.map(expenseDTO, Expense.class);
        expenseRepository.save(expense);
    }

    @Override
    public ArrayList<ExpenseDTO> getAllExpenses() {
        List<Expense> expenses = expenseRepository.findAll();
        return expenses.stream()
                .map(expense -> modelMapper.map(expense, ExpenseDTO.class))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    @Override
    public Page<ExpenseDTO> getAllExpensesPaginated(Pageable pageable) {
        Page<Expense> expenses = expenseRepository.findAll(pageable);
        return expenses.map(expense -> modelMapper.map(expense, ExpenseDTO.class));
    }

    @Override
    public ExpenseDTO getExpenseById(UUID id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));
        return modelMapper.map(expense, ExpenseDTO.class);
    }

    @Override
    public void updateExpense(UUID id, ExpenseDTO expenseDTO) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        modelMapper.map(expenseDTO, existingExpense);
        existingExpense.setId(id);
        expenseRepository.save(existingExpense);
    }

    @Override
    public boolean deleteExpense(UUID id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
}