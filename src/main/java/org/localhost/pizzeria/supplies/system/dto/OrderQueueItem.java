package org.localhost.pizzeria.supplies.system.dto;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class OrderQueueItem {
    private Long orderId;
    private List<Long> ingredientIds;
    private ZonedDateTime submittedAt;
}
