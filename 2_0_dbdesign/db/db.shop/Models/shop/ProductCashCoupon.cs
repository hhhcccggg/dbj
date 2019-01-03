using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 代金券扩展信息
    /// </summary>
    [Table("shop_productCashCoupons")]
    public class ProductCashCoupon:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 代金券面值
        /// 单位分
        /// 用于抵扣金额的值
        /// </summary>
        /// <value>The coupon value.</value>
        public int couponValue { get; set; }
        /// <summary>
        /// 节假日是否可用
        /// </summary>
        /// <value><c>true</c> if festival can use; otherwise, <c>false</c>.</value>
        public bool festivalCanUse { get; set; }
        /// <summary>
        /// 使用说明
        /// </summary>
        /// <value>The use info.</value>
        [MaxLength(1024)]
        public String useInfo { get; set; }
        /// <summary>
        /// 生效类型
        /// PAY_VALIDED:付款后立即生效
        /// PAY_NEXTDAY_VALIDED:付款后次日生效
        /// PAY_SPEC_HOUR_VALIDED:付款后指定小时生效
        /// </summary>
        /// <value>The type of the valid.</value>
        [Required]
        [MaxLength(128)]
        public String validType { get; set; }
        /// <summary>
        /// PAY_SPEC_HOUR_VALIDED:付款后指定小时生效
        /// 必须是>0的整数
        /// </summary>
        /// <value>The spec hours.</value>
        public int specHoursValid { get; set; }
        /// <summary>
        /// 生效后多少天内有效
        /// 0：表示长期有效
        /// -1：无此配置
        /// 其他数字，表示多少天内有效
        /// </summary>
        /// <value>The valid days.</value>
        public int validDays { get; set; }
        /// <summary>
        /// 生效后指定时间范围内生效
        /// </summary>
        /// <value>The valid start time.</value>
        [Column("validStartTime", TypeName = "timestamp")]
        public DateTime? validStartTime { get; set; }
        /// <summary>
        /// 生效后指定时间范围内生效
        /// </summary>
        /// <value>The valid end time.</value>
        [Column("validEndTime", TypeName = "timestamp")]
        public DateTime? validEndTime { get; set; }
        public long productId { get; set; }
        /// <summary>
        /// 存在规格有特殊定制?
        /// 预留
        /// </summary>
        /// <value>The product SKUI.</value>
        public long? productSKUId { get; set; }

    }
}
