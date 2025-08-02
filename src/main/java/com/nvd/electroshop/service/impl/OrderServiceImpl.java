package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.OrderItemRequest;
import com.nvd.electroshop.dto.request.OrderRequest;
import com.nvd.electroshop.dto.request.UpdateOrderStatusRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.OrderResponse;
import com.nvd.electroshop.entity.Order;
import com.nvd.electroshop.entity.OrderItem;
import com.nvd.electroshop.entity.Product;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.enums.OrderStatus;
import com.nvd.electroshop.exception.BadRequestException;
import com.nvd.electroshop.exception.ConflictException;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.mapper.OrderItemMapper;
import com.nvd.electroshop.mapper.OrderMapper;
import com.nvd.electroshop.repository.OrderRepository;
import com.nvd.electroshop.service.GlobalService;
import com.nvd.electroshop.service.OrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final GlobalService globalService;
    private final OrderRepository orderRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(
            GlobalService globalService,
            OrderRepository orderRepository,
            OrderItemMapper orderItemMapper,
            OrderMapper orderMapper
    ) {
        this.globalService = globalService;
        this.orderRepository = orderRepository;
        this.orderItemMapper = orderItemMapper;
        this.orderMapper = orderMapper;
    }

    @Override
    public ApiResponse<List<OrderResponse>> getAllUserOrders() {

        User user = globalService.getUserByToken();

        if(!orderRepository.existsByUser(user)) {
            throw new ResourceNotFoundException("Bạn chưa có đơn hàng nào");
        }

        List<Order> orderList = orderRepository.findByUser(user);
        List<OrderResponse> orderResponseList = orderMapper.mapToOrderResponseList(orderList);

        return new ApiResponse<>(1, orderResponseList);
    }

    @Override
    public ApiResponse<OrderResponse> createUserOrder(OrderRequest orderRequest) {

        Double totalOrder = getTotalOrder(orderRequest);
        User user = globalService.getUserByToken();
        LocalDateTime order_datetime = LocalDateTime.now();
        OrderStatus orderStatus = OrderStatus.PENDING;

        Order order = Order.builder()
                .order_datetime(order_datetime)
                .status(orderStatus)
                .total(totalOrder)
                .user(user)
                .build();

        List<OrderItem> orderItemList = orderItemMapper.mapToOrderItemList(order, orderRequest.getOrderItemRequestList());

        order.setOrderItems(orderItemList);

        order = orderRepository.save(order);

        OrderResponse orderResponse = orderMapper.mapToOrderResponse(order);

        return new ApiResponse<>(1, orderResponse);
    }

    @Override
    public ApiResponse<OrderResponse> getUserOrderById(Long id) {

        Order order = getUserOrder(id);
        OrderResponse orderResponse = orderMapper.mapToOrderResponse(order);

        return new ApiResponse<>(1, orderResponse);
    }

    @Override
    public ApiResponse<OrderResponse> updateUserOrder(Long id, OrderRequest orderRequest) {

        return null;
    }

    @Override
    public Message deleteUserOrder(Long id) {

        Order order = getUserOrder(id);

        if(order.getStatus() != OrderStatus.PENDING && order.getStatus() != OrderStatus.CONFIRMED) {

            throw new ConflictException("Bạn không thể hủy đơn hàng này");
        }

        orderRepository.delete(order);

        return new Message(1, "Xóa đơn hàng thành công");
    }

    //admin
    @Override
    public ApiResponse<List<OrderResponse>> getAllOrders() {

        List<Order> orderList = orderRepository.findAll();
        List<OrderResponse> orderResponseList = orderMapper.mapToOrderResponseList(orderList);

        return new ApiResponse<>(1, orderResponseList);
    }

    @Override
    public ApiResponse<OrderResponse> updateOrderStatus(Long id, UpdateOrderStatusRequest updateOrderStatusRequest) {

        String orderStatusRequest = updateOrderStatusRequest.getOrderStatus();
        boolean isValidOrderStatus = OrderStatus.isValidOrderStatus(orderStatusRequest);

        if (!isValidOrderStatus) {
            throw new BadRequestException("Dữ liệu trạng thái đơn hàng không hợp lệ");
        }

        OrderStatus orderStatus = OrderStatus.valueOf(orderStatusRequest.toUpperCase());

        Order order = getOrder(id);
        order.setStatus(orderStatus);

        order = orderRepository.save(order);

        OrderResponse orderResponse = orderMapper.mapToOrderResponse(order);

        return new ApiResponse<>(1, orderResponse);
    }

    // ins
    private Double getTotalOrder(OrderRequest orderRequest) {

        double totalOrder = 0.0;

        List<OrderItemRequest> orderItemRequestList = orderRequest.getOrderItemRequestList();

        for (OrderItemRequest orderItemRequest : orderItemRequestList) {

            Product product = globalService.getProductById(orderItemRequest.getProductId());
            totalOrder += product.getPrice() * orderItemRequest.getQuantity();
        }

        return totalOrder;
    }

    private Order getOrder(Long id) {

        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()) {
            throw new ResourceNotFoundException("Không tìm thấy đơn hàng");
        }

        return orderOptional.get();
    }

    private Order getUserOrder(Long id) {

        User user = globalService.getUserByToken();

        Optional<Order> orderOptional = orderRepository.findByIdAndUser(id, user);

        if(orderOptional.isEmpty()) {

            throw new ResourceNotFoundException("Không tìm thấy đơn hàng");
        }

        return orderOptional.get();
    }

}
