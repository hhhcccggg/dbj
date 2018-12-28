using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 物流配送区域
    /// </summary>
    [Table("shop_deliveryTemplateScopes")]
    public class DeliveryTemplateScope:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 支持配送区域，json对象
        /// </summary>
        /// <value>The delivery area.</value>
        [Required]
        public String deliveryArea { get; set; }
        public int item { get; set; }
        /// <summary>
        /// 价格，单位分
        /// </summary>
        /// <value>The item price.</value>
        public int itemPrice { get; set; }
        public int overItem { get; set; }
        /// <summary>
        /// 价格，单位分
        /// </summary>
        /// <value>The over item price.</value>
        public int overItemPrice { get; set; }
    }
}
