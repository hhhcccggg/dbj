using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models.shop
{
    [Table("shop_productSKUs")]
    public class ProductSKU : db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// sku编号
        /// </summary>
        /// <value>The sku number.</value>
        [MaxLength(128)]
        public string skuNumber { get; set; }
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
        /// <summary>
        /// 规格属性列表，json
        /// </summary>
        /// <value>The attrs.</value>
        public String attrs { get; set; }
        /// <summary>
        /// 重量kg
        /// </summary>
        /// <value>The weight.</value>
        public float weight { get; set; }
    }
}
