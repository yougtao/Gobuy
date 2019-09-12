package com.gobuy.order.pojo;


import org.springframework.data.annotation.Id;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "order_new")
public class Order {
    @Id
    private Long id;    // 订单id
    private Integer memberId;       // 买家id
    private Integer supplierId;     // 商户id
    private Integer orderStatus;    // 订单状态

    @NotNull
    private Long order_id;  // 交易号

    private Integer count;      // 商品数量
    @NotNull
    private Long totalAmount;  // 订单总价
    @NotNull
    private Long actualAmount;     // 应付金额

    @NotNull
    private Integer paymentChannel;    // 支付类型，1、在线支付，2、货到付款
    @NotNull
    private String paymentNo;           // 支付流水号

    private Integer receiverId;       // 收货地址信息id
    @Transient
    private Long shippingCode;    // 物流信息id

    private Timestamp createTime;       // 创建时间
    private Timestamp paymentTime;      // 支付时间
    private Timestamp deliveryTime;     // 发货时间
    private Timestamp settlementTime;   // 完成时间

    @Transient
    private String buyerMessage;    // 买家留言
    @Transient
    private Integer buyerRate;      // 买家是否已经评价 0为未评价 其他为id

    // 统计信息,待完善
    @Transient
    private Integer sourceType;     // 订单来源 1:app端，2：pc端，3：M端，4：微信端，5：手机qq端

    @Transient
    private List<OrderGoods> orderDetails;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Long actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Integer getPaymentChannel() {
        return paymentChannel;
    }

    public void setPaymentChannel(Integer paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getPaymentNo() {
        return paymentNo;
    }

    public void setPaymentNo(String paymentNo) {
        this.paymentNo = paymentNo;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Long getShippingCode() {
        return shippingCode;
    }

    public void setShippingCode(Long shippingCode) {
        this.shippingCode = shippingCode;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Timestamp paymentTime) {
        this.paymentTime = paymentTime;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Timestamp getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Timestamp settlementTime) {
        this.settlementTime = settlementTime;
    }

    public String getBuyerMessage() {
        return buyerMessage;
    }

    public void setBuyerMessage(String buyerMessage) {
        this.buyerMessage = buyerMessage;
    }

    public Integer getBuyerRate() {
        return buyerRate;
    }

    public void setBuyerRate(Integer buyerRate) {
        this.buyerRate = buyerRate;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public List<OrderGoods> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderGoods> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
