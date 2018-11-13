using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models.shop
{
    /// <summary>
    /// 商品属性规格名
    /// </summary>
    [Table("shop_productAttris")]
    public class ProductAttri:db.common.BaseModelWithTime<long>
    {
        [Required]
        [MaxLength(30)]
        public String name { get; set; }
        /// <summary>
        /// 商品分类
        /// </summary>
        /// <value>The category identifier.</value>
        public long categoryId { get; set; }
        /// <summary>
        /// 父属性
        /// </summary>
        /// <value>The parent identifier.</value>
        public long parentId { get; set; }
    }
}
