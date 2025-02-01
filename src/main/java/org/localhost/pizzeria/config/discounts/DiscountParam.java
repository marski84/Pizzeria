package org.localhost.pizzeria.config.discounts;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.List;

@Getter
@Builder
public class DiscountParam {
    @DecimalMin("0.0")
    @DecimalMax("1.0")
    private BigDecimal discountLevel;

    @Min(0)
    @Max(100)
    private int ageLimit;

    @NotEmpty
    private List<DayOfWeek> weekdays;

    private boolean studentStatusRequired;

    public boolean isApplicable(int age, boolean isStudent, DayOfWeek currentDay) {
        boolean ageCheck = ageLimit == 0 || age <= ageLimit;
        boolean studentCheck = studentStatusRequired && isStudent;
        boolean dayCheck = weekdays.contains(currentDay);

        return ageCheck && studentCheck && dayCheck;
    }
}
