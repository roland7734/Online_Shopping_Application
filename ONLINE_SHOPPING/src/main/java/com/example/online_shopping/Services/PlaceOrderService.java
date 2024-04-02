package com.example.online_shopping.Services;

public class PlaceOrderService {

    public static boolean placeOrderWithoutPayment(int userid, String address, String voucher) {
        int orderid = OrderService.createNewOrderId();
        return addTableRows(orderid, userid, address, voucher);
    }
    public static boolean placeOrderWithPayment(int userid, String address, String voucher) {
        int orderid = OrderService.createNewOrderId();
        boolean flag1 = addTableRows(orderid, userid, address, voucher);
        boolean flag2 = PaymentService.addPayment(orderid);
        return flag1 && flag2;
    }
    public static boolean addTableRows(int orderid, int userid, String address, String voucher) {
        boolean flag1 = OrderService.createOrder(orderid, userid, address);
        boolean flag2 = OrderItemService.createOrderItems(orderid);
        boolean flag3 = ProductService.decreaseBoughtProductsStockQuantity(userid);
        boolean flag4 = CartItemService.deleteCart(userid);
        boolean flag5 = true;
        boolean flag6 = true;
        if (!voucher.isEmpty()) {
            flag5 = OrderService.addVoucherToOrder(orderid, voucher);
            flag6 = VoucherService.setVoucherInactive(voucher);
        }
        return flag1 && flag2 && flag3 && flag4 && flag5 && flag6;
    }

}
