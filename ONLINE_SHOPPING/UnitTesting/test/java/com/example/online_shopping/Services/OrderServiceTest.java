package com.example.online_shopping.Services;

import com.example.online_shopping.Services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderServiceTest {

    @Test
    void getNextStatus_Pending_ShouldReturnOrderConfirmed() {
        Assertions.assertEquals("Order Confirmed", OrderService.getNextStatus("Pending"));
    }

    @Test
    void getNextStatus_OrderConfirmed_ShouldReturnShipped() {
        assertEquals("Shipped", OrderService.getNextStatus("Order Confirmed"));
    }

    @Test
    void getNextStatus_Shipped_ShouldReturnOutForDelivery() {
        assertEquals("Out for Delivery", OrderService.getNextStatus("Shipped"));
    }

    @Test
    void getNextStatus_OutForDelivery_ShouldReturnDelivered() {
        assertEquals("Delivered", OrderService.getNextStatus("Out for Delivery"));
    }

    @Test
    void getNextStatus_UnknownStatus_ShouldReturnNoAction() {
        assertEquals("No Action", OrderService.getNextStatus("Unknown"));
    }
}
