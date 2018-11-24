using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models.shop
{
    [Table("shop_productOrders")]
    public class ProductOrder:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 实付金额，单位为元，精确到小数点2位
        /// </summary>
        /// <value>The payment.</value>
        public float payment { get; set; }
        /// <summary>
        /// 支付类型，1、在线支付2、货到付款
        /// </summary>
        /// <value>The type of the payment.</value>
        public int paymentType { get; set; }
        /// <summary>
        /// 邮费，单位元，精确到小数点2位
        /// </summary>
        /// <value>The delivery fee.</value>
        public float deliveryFee { get; set; }
        /// <summary>
        /// 交易状态，1、未付款2、已付款3、未发货、已发货5、申请退款6、已退款7、交易成功8、交易关闭
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
        /// <summary>
        /// 交易更新时间
        /// </summary>
        /// <value>The update time.</value>
        [Column("updateTime", TypeName = "timestamp")]
        public DateTime updateTime { get; set; }
        /// <summary>
        /// 付款时间
        /// </summary>
        /// <value>The payment time.</value>
        [Column("paymentTime", TypeName = "timestamp")]
        public DateTime paymentTime { get; set; }
        /// <summary>
        /// 发货时间
        /// </summary>
        /// <value>The payment time.</value>
        [Column("deliveryTime", TypeName = "timestamp")]
        public DateTime deliveryTime { get; set; }
        /// <summary>
        /// 交易结束时间
        /// </summary>
        /// <value>The end time.</value>
        [Column("endTime", TypeName = "timestamp")]
        public DateTime endTime { get; set; }
        /// <summary>
        /// 交易关闭时间
        /// </summary>
        /// <value>The close time.</value>
        [Column("closeTime", TypeName = "timestamp")]
        public DateTime closeTime { get; set; }
        /// <summary>
        /// 物流名字
        /// </summary>
        /// <value>The name of the delivery.</value>
        [MaxLength(30)]
        public String deliveryName { get; set; }
        /// <summary>
        /// 物流类型，主要是物流公司
        /// </summary>
        /// <value>The type of the delivery.</value>
        [MaxLength(30)]
        public String deliveryType { get; set; }
        /// <summary>
        /// 物流单号
        /// </summary>
        /// <value>The delivery code.</value>
        [MaxLength(128)]
        public String deliveryCode { get; set; }
        public long userId { get; set; }
        public long sellerId { get; set; }
        /// <summary>
        /// 买家留言
        /// </summary>
        /// <value>The buyer comment.</value>
        [MaxLength(512)]
        public String buyerComment { get; set; }
        /// <summary>
        /// 买家是否已评价
        /// </summary>
        /// <value><c>true</c> if buyer rate; otherwise, <c>false</c>.</value>
        public bool buyerRate { get; set; }
    }
}
