package com.payable.expense_management.dto;

import com.payable.expense_management.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseSummaryDTO {
    private Map<Category, BigDecimal> totalPerCategory;
    private Category highestCategory;
}