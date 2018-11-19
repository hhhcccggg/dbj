using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models.shop
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
        public float price { get; set; }
        public float totalFee { get; set; }
    }
}
