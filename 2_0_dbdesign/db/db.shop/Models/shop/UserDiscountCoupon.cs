using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 用户领取的优惠券
    /// </summary>
    [Table("shop_userDiscountCoupons")]
    public class UserDiscountCoupon:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 优惠券ID
        /// </summary>
        /// <value>The coupon identifier.</value>
        public long couponId { get; set; }
        public long userId { get; set; }
        /// <summary>
        /// 优惠券状态
        /// UNUSED:未使用
        /// USED:已使用
        /// </summary>
        /// <value>The state.</value>
        [Required]
        [MaxLength(128)]
        public String state { get; set; }
    }
}
