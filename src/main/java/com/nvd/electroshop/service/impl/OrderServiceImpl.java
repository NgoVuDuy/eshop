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
import com.nvd.electroshop.mapper.ProductMapper;
import com.nvd.electroshop.repository.OrderRepository;
import com.nvd.electroshop.repository.ProductRepository;
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
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;

    public OrderServiceImpl(
            GlobalService globalService,
            OrderRepository orderRepository,
            OrderItemMapper orderItemMapper,
            OrderMapper orderMapper,
            ProductMapper productMapper,
            ProductRepository productRepository
    ) {
        this.globalService = globalService;
        this.orderRepository = orderRepository;
        this.orderItemMapper = orderItemMapper;
        this.orderMapper = orderMapper;
        this.productMapper = productMapper;
        this.productRepository = productRepository;
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

        Double totalOrder = getTotalOrder(orderRequest); // Tính tổng giá của đơn hàng
        User user = globalService.getUserByToken(); // Lấy ra thông tin người dùng
        LocalDateTime order_datetime = LocalDateTime.now(); // Lấy thời gian hiện tại
        OrderStatus orderStatus = OrderStatus.PENDING; // Trạng thái đơn hàng

        Order order = Order.builder()
                .order_datetime(order_datetime)
                .status(orderStatus)
                .total(totalOrder)
                .user(user)
                .build();

        List<OrderItemRequest> orderItemRequestList = orderRequest.getOrderItemRequestList();

        List<OrderItem> orderItemList = orderItemMapper.mapToOrderItemList(order, orderItemRequestList);
        order.setOrderItems(orderItemList);
        order = orderRepository.save(order);

        // Cập nhật lại số lượng sản phẩm trong kho
        for (OrderItemRequest orderItemRequest : orderItemRequestList) {

            int quantity = orderItemRequest.getQuantity();
            Product product = globalService.getProductById(orderItemRequest.getProductId());

            product.setStockQuantity(product.getStockQuantity() - quantity);

            productRepository.save(product);
        }

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
        // Cập nhật lại số lượng kho
        List<OrderItem> orderItemList = order.getOrderItems();
        for (OrderItem orderItem : orderItemList) {

            Product product = orderItem.getProduct();

            product.setStockQuantity(product.getStockQuantity() + orderItem.getQuantity());

            productRepository.save(product);
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
