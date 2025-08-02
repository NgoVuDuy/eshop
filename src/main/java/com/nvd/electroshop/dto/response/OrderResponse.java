package com.nvd.electroshop.dto.response;

import com.nvd.electroshop.entity.OrderItem;
import com.nvd.electroshop.enums.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;
    private LocalDateTime order_datetime;
    private OrderStatus status;
    private String total;
    private List<OrderItemResponse> orderItemResponseList;
}
