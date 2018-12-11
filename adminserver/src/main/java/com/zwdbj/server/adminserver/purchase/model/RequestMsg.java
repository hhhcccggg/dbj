package com.zwdbj.server.adminserver.purchase.model;

//二次验证的请求参数
public class RequestMsg {

    //产品标识符（在itunes store应用内定义的产品ID，例如com.公司名.产品名.道具名(com.xxxx.video.vip)）
    private String productIdentifier;
    //交易状态:
    //  Purchased	购买成功
    //  Restored	恢复购买
    //  Failed	    失败
    //  Deferred	等待确认，儿童模式需要询问家长同意）
    private String state;
    //二次验证的重要依据
    private String receipt;
    //交易标识符
    private String transactionIdentifier;

    public String getProductIdentifier() {
        return productIdentifier;
    }

    public void setProductIdentifier(String productIdentifier) {
        this.productIdentifier = productIdentifier;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public void setTransactionIdentifier(String transactionIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
    }
}
