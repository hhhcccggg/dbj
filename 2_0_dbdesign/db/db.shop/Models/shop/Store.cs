using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 店铺
    /// </summary>
    [Table("shop_stores")]
    public class Store :db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 店铺编号
        /// </summary>
        /// <value>The seller number.</value>
        [MaxLength(128)]
        public String storeNumber { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        /// <value>The name.</value>
        [Required]
        [MaxLength(50)]
        public String name { get; set; }
        [MaxLength(128)]
        public String subName { get; set; }
        [MaxLength(128)]
        public String marketName { get; set; }
        [MaxLength(512)]
        public String shareDesc { get; set; }
        [MaxLength(2048)]
        public String description { get; set; }
        [MaxLength(512)]
        public String logoUrl { get; set; }
        /// <summary>
        /// 推荐指数
        /// </summary>
        /// <value>The index of the recommend.</value>
        public long recommendIndex { get; set; }
        /// <summary>
        /// 经度
        /// </summary>
        /// <value>The longitude.</value>
        public float longitude { get; set; }
        /// <summary>
        /// 维度
        /// </summary>
        /// <value>The latitude.</value>
        public float latitude { get; set; }
        /// <summary>
        /// 地址
        /// </summary>
        /// <value>The address.</value>
        [MaxLength(512)]
        public String address { get; set; }
        /// <summary>
        /// 店铺类型>>SELF:自营THIRD:第三方入驻商家OFFLINE:线下门店
        /// </summary>
        /// <value>The type.</value>
        [MaxLength(50)]
        [Required]
        public string type { get; set; }
        /// <summary>
        /// 店铺等级
        /// </summary>
        /// <value>The level.</value>
        public int level { get; set; }
        /// <summary>
        /// 主营类目
        /// </summary>
        /// <value>The category identifier.</value>
        public long categoryId { get; set; }
        public String categoryLevel { get; set; }
        public int cityId { get; set; }
        [MaxLength(512)]
        public String cityLevel { get; set; }
        /// <summary>
        /// 在平台开店的有效期
        /// </summary>
        /// <value>The expire time.</value>
        [Column("expireTime", TypeName = "timestamp")]
        public DateTime expireTime { get; set; }
        [Required]
        [MaxLength(50)]
        public String contactName { get; set; }
        [Required]
        [MaxLength(20)]
        public String contactPhone { get; set; }
        [MaxLength(20)]
        public String qq { get; set; }
        /// <summary>
        /// 综合评分
        /// </summary>
        /// <value>The grade.</value>
        public int grade { get; set; }
        /// <summary>
        /// 0：正常1：关闭,2审核中
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
        /// <summary>
        /// 是否已审核通过
        /// </summary>
        /// <value><c>true</c> if is reviewed; otherwise, <c>false</c>.</value>
        public bool reviewed { get; set; }
        /// <summary>
        /// 是否停止服务
        /// </summary>
        /// <value><c>true</c> if is stop service; otherwise, <c>false</c>.</value>
        public bool stopService { get; set; }
        /// <summary>
        /// 封面图
        /// </summary>
        /// <value>The main conver image.</value>
        [MaxLength(128)]
        public String mainConverImage { get; set; }
        /// <summary>
        /// 封面图地址，多个用,隔开
        /// </summary>
        /// <value>The cover images.</value>
        [MaxLength(1024)]
        public String coverImages { get; set; }
        /// <summary>
        /// 法律主体，也就是该店铺属于谁
        /// 卖家ID
        /// 企业标识
        /// </summary>
        /// <value>The legal subject identifier.</value>
        [Required]
        public long legalSubjectId { get; set; }
    }
}
