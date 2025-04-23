package com.payable.expense_management.service;

import com.payable.expense_management.dto.ExpenseSummaryDTO;
import java.time.LocalDate;

public interface ExpenseSummaryService {
    ExpenseSummaryDTO getExpenseSummary();

    ExpenseSummaryDTO getExpenseSummaryByDateRange(LocalDate startDate, LocalDate endDate);
}