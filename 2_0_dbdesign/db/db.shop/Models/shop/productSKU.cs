using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
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
        /// 原价，单位分
        /// </summary>
        /// <value>The original price.</value>
        public int originalPrice { get; set; }
        /// <summary>
        /// 促销价,单位分
        /// </summary>
        /// <value>The promotion price.</value>
        public int promotionPrice { get; set; }
        /// <summary>
        /// 库存
        /// </summary>
        /// <value>The inventory.</value>
        public int inventory { get; set; }
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
        /// 重量g,克
        /// </summary>
        /// <value>The weight.</value>
        public int weight { get; set; }
    }
}
