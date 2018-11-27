using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    [Table("shop_productCarts")]
    public class ProductCart:db.common.BaseModelWithTime<long>
    {
        public long userId { get; set; }
        public long sellerId { get; set; }
        public long productId { get; set; }
        public long productskuId { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        /// <value>The item.</value>
        public long item { get; set; }
        /// <summary>
        /// 价格
        /// </summary>
        /// <value>The price.</value>
        public float price { get; set; }
        public string ip { get; set; }
        public string ua { get; set; }
        public long expireTime { get; set; }
    }
}
