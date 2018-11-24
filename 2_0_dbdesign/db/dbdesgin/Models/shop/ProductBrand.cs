using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models.shop
{
    [Table("shop_productBrands")]
    public class ProductBrand : db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 品牌名字
        /// </summary>
        /// <value>The name.</value>
        [Required]
        [MaxLength(20)]
        public String name { get; set; }
        [MaxLength(512)]
        public String imageUrl { get; set; }
        [MaxLength(1024)]
        public String description { get; set; }
        public int orderIndex { get; set; }
    }
}
