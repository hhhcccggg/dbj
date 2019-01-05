using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    [Table("shop_productCarts")]
    public class ProductCart:db.common.BaseModelWithTime<long>
    {
        public long userId { get; set; }
        /// <summary>
        /// 店铺ID
        /// </summary>
        /// <value>The store identifier.</value>
        public long storeId { get; set; }
        public long productId { get; set; }
        public long productskuId { get; set; }
        /// <summary>
        /// 数量
        /// </summary>
        /// <value>The item.</value>
        public int item { get; set; }
        /// <summary>
        /// 价格，单位分
        /// </summary>
        /// <value>The price.</value>
        public int price { get; set; }
        [MaxLength(50)]
        public string ip { get; set; }
        [MaxLength(128)]
        public string ua { get; set; }
        /// <summary>
        /// 过期时间，单位分
        /// </summary>
        /// <value>The expire time.</value>
        public long expireTime { get; set; }
    }
}
