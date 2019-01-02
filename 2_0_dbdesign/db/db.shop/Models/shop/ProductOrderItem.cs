using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    [Table("shop_productOrderItems")]
    public class ProductOrderItem:db.common.BaseModelWithTime<long>
    {
        public long productId { get; set; }
        public long productskuId { get; set; }
        public long orderId { get; set; }
        /// <summary>
        /// 商品数量
        /// </summary>
        /// <value>The number.</value>
        public int num { get; set; }
        [MaxLength(512)]
        public String title { get; set; }
        /// <summary>
        /// 单价，单位分
        /// </summary>
        /// <value>The price.</value>
        public int price { get; set; }
        /// <summary>
        /// 总费用，单位分
        /// </summary>
        /// <value>The total fee.</value>
        public int totalFee { get; set; }
    }
}
