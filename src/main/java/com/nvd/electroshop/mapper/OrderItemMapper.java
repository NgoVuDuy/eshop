package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.request.OrderItemRequest;
import com.nvd.electroshop.dto.response.OrderItemResponse;
import com.nvd.electroshop.dto.response.ProductResponse;
import com.nvd.electroshop.entity.Order;
import com.nvd.electroshop.entity.OrderItem;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.repository.ProductRepository;
import com.nvd.electroshop.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemMapper {

    @Autowired
    GlobalService globalService;

    @Autowired
    ProductMapper productMapper;

    public OrderItemResponse mapToOrderItemResponse(OrderItem orderItem) {

        Product product = orderItem.getProduct();

        ProductResponse productResponse = productMapper.mapToProductResponse(product);

        return OrderItemResponse.builder()
                .product(productResponse)
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .build();
    }

    public List<OrderItemResponse> mapToOrderItemResponseList(List<OrderItem> orderItemList) {

        return orderItemList.stream().map(this::mapToOrderItemResponse).toList();
    }

    public OrderItem mapToOrderItem(Order order, OrderItemRequest orderItemRequest) {

        Product product = globalService.getProductById(orderItemRequest.getProductId());

        Double price = product.getPrice() * orderItemRequest.getQuantity();

        return OrderItem.builder()
                .product(product)
                .order(order)
                .quantity(orderItemRequest.getQuantity())
                .price(price)
                .build();

    }

    public List<OrderItem> mapToOrderItemList(Order order, List<OrderItemRequest> orderItemRequestList) {

        return orderItemRequestList.stream().map(orderItemRequest -> this.mapToOrderItem(order,orderItemRequest)).toList();
    }
}
