using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 优惠券
    /// </summary>
    [Table("shop_discountCoupons")]
    public class DiscountCoupon:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 名字
        /// </summary>
        /// <value>The name.</value>
        [Required]
        [MaxLength(50)]
        public string name { get; set; }
        /// <summary>
        /// 发放的数量
        /// </summary>
        /// <value>The coupon count.</value>
        public int couponCount { get; set; }
        /// <summary>
        /// 优惠的形式
        /// CASH: 现金优惠
        /// DISCOUNT:折扣
        /// </summary>
        /// <value>The type of the discount.</value>
        [Required]
        [MaxLength(128)]
        public string discountType { get; set; }
        /// <summary>
        /// 优惠的值
        /// discountType=CASH, 这里存的是金额，单位分
        /// discountType=DISCOUNT,这里存的是折扣，具体折扣换算discountValue/10.f,比如discountValue=5，那么折扣就是5/10.0f=0.5f
        /// </summary>
        /// <value>The discount value.</value>
        public int discountValue { get; set; }
        /// <summary>
        /// 限制条件，满{limitMoney}金额后才可以使用
        /// 单位分
        /// 0:没有限制
        /// </summary>
        /// <value>The limit money.</value>
        public int limitMoney { get; set; }
        /// <summary>
        /// 每一个人限领数量
        /// 0:没有限制
        /// </summary>
        /// <value>The limit get per person.</value>
        public int limitGetPerPerson { get; set; }
        /// <summary>
        /// 使用说明    
        /// </summary>
        /// <value>The use info.</value>
        public string useInfo { get; set; }
        /// <summary>
        /// 仅支持原价购买的商品才能使用
        /// </summary>
        /// <value><c>true</c> if only support origin product; otherwise, <c>false</c>.</value>
        public bool onlySupportOriginProduct { get; set; }
        /// <summary>
        /// 生效时间段
        /// 如果未指定，则即时生效
        /// </summary>
        /// <value>The valid start time.</value>
        [Column("validStartTime", TypeName = "timestamp")]
        public DateTime? validStartTime { get; set; }
        /// <summary>
        /// 生效时间段
        /// 如果未指定，则永久有效
        /// </summary>
        /// <value>The valid end time.</value>
        [Column("validEndTime", TypeName = "timestamp")]
        public DateTime? validEndTime { get; set; } 
        /// <summary>
        /// 店铺ID
        /// </summary>
        /// <value>The store identifier.</value>
        public long? storeId { get; set; }
        /// <summary>
        /// 商家ID
        /// </summary>
        /// <value>The legal subject identifier.</value>
        public long? legalSubjectId { get; set; }
    }
}
