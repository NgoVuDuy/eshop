package com.nvd.electroshop.dto.request;

import com.nvd.electroshop.entity.OrderItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    private List<OrderItemRequest> orderItemRequestList;
}
