package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.response.OrderItemResponse;
import com.nvd.electroshop.dto.response.OrderResponse;
import com.nvd.electroshop.entity.Order;
import com.nvd.electroshop.entity.OrderItem;
import com.nvd.electroshop.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private GlobalService globalService;

    public OrderResponse mapToOrderResponse(Order order) {

        List<OrderItem> orderItemList = order.getOrderItems();
        List<OrderItemResponse> orderItemResponseList = orderItemMapper.mapToOrderItemResponseList(orderItemList);

        return OrderResponse.builder()
                .id(order.getId())
                .status(order.getStatus())
                .total(globalService.formatCurrency(order.getTotal()))
                .order_datetime(order.getOrder_datetime())
                .orderItemResponseList(orderItemResponseList)
                .build();
    }

    public List<OrderResponse> mapToOrderResponseList(List<Order> orderList) {

        return orderList.stream().map(this::mapToOrderResponse).toList();
    }
}
