package com.payable.expense_management.service;

import com.payable.expense_management.dto.ExpenseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.UUID;

public interface ExpenseService {
    void createExpense(ExpenseDTO expenseDTO);

    ArrayList<ExpenseDTO> getAllExpenses();

    Page<ExpenseDTO> getAllExpensesPaginated(Pageable pageable);

    ExpenseDTO getExpenseById(UUID id);

    void updateExpense(UUID id, ExpenseDTO expenseDTO);

    boolean deleteExpense(UUID id);
}
