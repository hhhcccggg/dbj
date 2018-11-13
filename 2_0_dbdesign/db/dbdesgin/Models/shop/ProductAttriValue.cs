using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models.shop
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
        /// <summary>
        /// 其他附加数据
        /// </summary>
        /// <value>The extra value.</value>
        public String extraValue { get; set; }
        public long productAttriId { get; set; }
    }
}
