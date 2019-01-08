package com.zwdbj.server.adminserver.service.shop.service.productOrder.model;

import com.zwdbj.server.adminserver.service.shop.service.receiveAddress.model.ReceiveAddressModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(value = "订单的简单model")
public class ProductOrderModel {
    @ApiModelProperty(value = "订单id")
    private long id;
    @ApiModelProperty(value = "订单创建时间")
    private Date createTime;
    @ApiModelProperty(value = "商品的标题")
    private String title;
    @ApiModelProperty(value = "商品的单价 单位为分")
    private int price;
    @ApiModelProperty(value = "购买商品的数量")
    private int num;
    @ApiModelProperty(value = "订单的状态")
    private String status;
    @ApiModelProperty(value = "收货地址model")
    private ReceiveAddressModel addressModel;
    @ApiModelProperty(value = "此订单的实付金额,单位为分")
    private int actualPayment;
    @ApiModelProperty(value = "此订单使用的金币抵扣数")
    private int useCoin;
    @ApiModelProperty(value = "订单总金额，单位为分")
    private int payment;

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public int getUseCoin() {
        return useCoin;
    }

    public void setUseCoin(int useCoin) {
        this.useCoin = useCoin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ReceiveAddressModel getAddressModel() {
        return addressModel;
    }

    public void setAddressModel(ReceiveAddressModel addressModel) {
        this.addressModel = addressModel;
    }

    public int getActualPayment() {
        return actualPayment;
    }

    public void setActualPayment(int actualPayment) {
        this.actualPayment = actualPayment;
    }
}
