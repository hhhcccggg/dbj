using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models.shop
{
    [Table("shop_productSKUs")]
    public class productSKU : db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 产品编号
        /// </summary>
        /// <value>The product identifier.</value>
        public long productId { get; set; }
        /// <summary>
        /// 原价
        /// </summary>
        /// <value>The original price.</value>
        public float originalPrice { get; set; }
        /// <summary>
        /// 促销价
        /// </summary>
        /// <value>The promotion price.</value>
        public float promotionPrice { get; set; }
        /// <summary>
        /// 库存
        /// </summary>
        /// <value>The inventory.</value>
        public long inventory { get; set; }
        /// <summary>
        /// 销量
        /// </summary>
        /// <value>The sales.</value>
        public long sales { get; set; }
    }
}
