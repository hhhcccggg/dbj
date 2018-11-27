using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 商品属性规格值
    /// </summary>
    [Table("shop_productAttriValues")]
    public class ProductAttriValue:db.common.BaseModelWithTime<long>
    {
        [Required]
        [MaxLength(30)]
        public String attiValue { get; set; }
        public long productAttriId { get; set; }
    }
}
