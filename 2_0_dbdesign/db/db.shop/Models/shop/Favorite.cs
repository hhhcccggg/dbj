using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 收藏夹
    /// </summary>
    [Table("shop_Favorites")]
    public class Favorite:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 收藏的目标对象ID
        /// 店铺、商家、商品SKUID
        /// </summary>
        /// <value>The target identifier.</value>
        public long targetId { get; set; }
        /// <summary>
        /// 收藏的目标对象类型
        /// LAGALSUBJECT:商家
        /// STORE:店铺
        /// PRODUCTSKU:商品
        /// </summary>
        /// <value>The type of the target.</value>
        [Required]
        [MaxLength(128)]
        public String targetType { get; set; }
        public long userId { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        /// <value>The title.</value>
        [Required]
        [MaxLength(1024)]
        public string title { get; set; }
        /// <summary>
        /// 缩略图
        /// </summary>
        /// <value>The image URL.</value>
        [MaxLength(1024)]
        [Required]
        public String imageUrl { get; set; }
        /// <summary>
        /// 价格
        /// 单位分
        /// 店铺、商家忽略
        /// </summary>
        /// <value>The price.</value>
        public int price { get; set; }
    }
}
