package com.payable.expense_management.controller;

import com.payable.expense_management.dto.ExpenseDTO;
import com.payable.expense_management.dto.StandardResponse;
import com.payable.expense_management.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<StandardResponse> createExpense(@Valid @RequestBody ExpenseDTO expenseDTO) {
        expenseService.createExpense(expenseDTO);
        return new ResponseEntity<>(
                new StandardResponse(201, "Expense created successfully", expenseDTO),
                HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<StandardResponse> getAllExpenses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "date") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<ExpenseDTO> expenses = expenseService.getAllExpensesPaginated(pageRequest);

        return new ResponseEntity<>(
                new StandardResponse(200, "Expenses retrieved successfully", expenses),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StandardResponse> getExpenseById(@PathVariable UUID id) {
        ExpenseDTO expense = expenseService.getExpenseById(id);
        return new ResponseEntity<>(
                new StandardResponse(200, "Expense retrieved successfully", expense),
                HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StandardResponse> updateExpense(
            @PathVariable UUID id,
            @Valid @RequestBody ExpenseDTO expenseDTO) {
        expenseService.updateExpense(id, expenseDTO);
        return new ResponseEntity<>(
                new StandardResponse(200, "Expense updated successfully", expenseDTO),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StandardResponse> deleteExpense(@PathVariable UUID id) {
        boolean deleted = expenseService.deleteExpense(id);
        return new ResponseEntity<>(
                new StandardResponse(200, deleted ? "Expense deleted successfully" : "Expense not found", null),
                deleted ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }
}
