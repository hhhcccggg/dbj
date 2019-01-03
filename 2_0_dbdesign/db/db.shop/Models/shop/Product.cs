using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 产品
    /// </summary>
    [Table("shop_products")]
    public class Product:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 产品类型O:实物产品1:虚拟商品
        /// </summary>
        /// <value>The type of the product.</value>
        public int productType { get; set; }
        /// <summary>
        /// 产品详细类型
        /// DELIVERY: 实物产品
        /// NODELIVERY:虚拟商品(不需要物流)
        /// CARD:卡券(服务中套餐)，关联[ProductCard]表
        /// CASHCOUPON:代金券，类似70抵100，关联[ProductCashCoupon]表
        /// </summary>
        /// <value>The type of the product detail.</value>
        [Required]
        [MaxLength(128)]
        public String productDetailType { get; set; }
        /// <summary>
        /// 产品编码
        /// </summary>
        /// <value>The number identifier.</value>
        [MaxLength(128)]
        public String numberId { get; set; }
        [Required]
        [MaxLength(128)]
        public String name { get; set; }
        [MaxLength(512)]
        public String subName { get; set; }
        [MaxLength(512)]
        public String searchName { get; set; }
        [MaxLength(512)]
        public String marketName { get; set; }
        /// <summary>
        /// 商品卖点
        /// </summary>
        /// <value>The seller point.</value>
        [MaxLength(512)]
        public String sellerPoint { get; set; }
        [Required]
        public long categoryId { get; set; }
        [MaxLength(1024)]
        public String categoryLevel { get; set; }
        /// <summary>
        /// 品牌ID
        /// </summary>
        /// <value>The brand identifier.</value>
        public long brandId { get; set; }
        /// <summary>
        /// 分享描述
        /// </summary>
        /// <value>The share desc.</value>
        [MaxLength(512)]
        public String shareDesc { get; set; }
        /// <summary>
        /// 店铺ID
        /// </summary>
        /// <value>The seller identifier.</value>
        public long storeId { get; set; }
        /// <summary>
        /// 评论数
        /// </summary>
        /// <value>The comment count.</value>
        public long commentCount { get; set; }
        /// <summary>
        /// 评分
        /// </summary>
        /// <value>The grade.</value>
        public int grade { get; set; }
        /// <summary>
        /// 销量
        /// </summary>
        /// <value>The sales.</value>
        public long sales { get; set; }
        /// <summary>
        /// 库存
        /// </summary>
        /// <value>The inventory.</value>
        public long inventory { get; set; }
        /// <summary>
        /// 产品价格上限,单位分
        /// </summary>
        /// <value>The price up.</value>
        public int priceUp { get; set; }
        /// <summary>
        /// 产品价格下限,单位分
        /// </summary>
        /// <value>The price down.</value>
        public int priceDown { get; set; }
        /// <summary>
        /// 产品图，图片地址json数组
        /// </summary>
        /// <value>The image urls.</value>
        public String imageUrls { get; set; }
        /// <summary>
        /// 产品视频url
        /// </summary>
        /// <value>The video URL.</value>
        [MaxLength(1024)]
        public String videoUrl { get; set; }
        /// <summary>
        /// 商品分组
        /// </summary>
        /// <value>The product group identifier.</value>
        public long productGroupId { get; set; }
        /// <summary>
        /// 是否参与会员打折
        /// </summary>
        /// <value><c>true</c> if is join member discount; otherwise, <c>false</c>.</value>
        public bool joinMemberDiscount { get; set; }
        /// <summary>
        /// 是否需要物流
        /// </summary>
        /// <value><c>true</c> if is need delivery; otherwise, <c>false</c>.</value>
        public bool needDelivery { get; set; }
        /// <summary>
        /// 通用物流价格,单位分
        /// </summary>
        /// <value>The universal delivery price.</value>
        public int universalDeliveryPrice { get; set; }
        /// <summary>
        /// 物流模板
        /// </summary>
        /// <value>The deliverytemplate identifier.</value>
        public long deliverytemplateId { get; set; }
        /// <summary>
        /// 是否上架
        /// </summary>
        /// <value><c>true</c> if is publish; otherwise, <c>false</c>.</value>
        public bool publish { get; set; }
        /// <summary>
        /// 指定上架时间，时间戳,单位秒
        /// </summary>
        /// <value>The specify publish time.</value>
        public long specifyPublishTime { get; set; }
        /// <summary>
        /// 商品详情
        /// </summary>
        /// <value>The detail description.</value>
        public String detailDescription { get; set; }
        /// <summary>
        /// 重量g克
        /// </summary>
        /// <value>The weight.</value>
        public int weight { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        /// <value>The notes.</value>
        [MaxLength(512)]
        public String notes { get; set; }
        /// <summary>
        /// 是否限购
        /// 0：表示不限购
        /// 大于0数字表示没人只能买商品的数量
        /// </summary>
        /// <value>The limit per person.</value>
        public int limitPerPerson { get; set; }
    }
}
