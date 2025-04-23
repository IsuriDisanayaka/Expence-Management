package com.payable.expense_management.controller;

import com.payable.expense_management.dto.ExpenseSummaryDTO;
import com.payable.expense_management.dto.StandardResponse;
import com.payable.expense_management.service.ExpenseSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/expenses/summary")
@CrossOrigin
public class ExpenseSummaryController {

    @Autowired
    private ExpenseSummaryService summaryService;

    @GetMapping
    public ResponseEntity<StandardResponse> getExpenseSummary() {
        ExpenseSummaryDTO summary = summaryService.getExpenseSummary();
        return new ResponseEntity<>(
                new StandardResponse(200, "Expense summary retrieved successfully", summary),
                HttpStatus.OK);
    }

    @GetMapping("/by-date")
    public ResponseEntity<StandardResponse> getExpenseSummaryByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        ExpenseSummaryDTO summary = summaryService.getExpenseSummaryByDateRange(startDate, endDate);
        return new ResponseEntity<>(
                new StandardResponse(200, "Expense summary for date range retrieved successfully", summary),
                HttpStatus.OK);
    }
}