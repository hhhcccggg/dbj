using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;
namespace db.o2o.Models
{
    /// <summary>
    /// 线下门店
    /// </summary>
    [Table("o2o_offlineStores")]
    public class OfflineStore : BaseModelWithTime<long>
    {
        /// <summary>
        /// 名称
        /// </summary>
        /// <value>The title.</value>
        [Required]
        [MaxLength(128)]
        public String name { get; set; }
        [MaxLength(128)]
        public String subName { get; set; }
        [MaxLength(128)]
        public String marketName { get; set; }
        [MaxLength(1024)]
        public String description { get; set; }
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
        /// 是否停止服务
        /// </summary>
        /// <value><c>true</c> if is stop service; otherwise, <c>false</c>.</value>
        public bool isStopService { get; set; }
        /// <summary>
        /// 是否已审核通过
        /// </summary>
        /// <value><c>true</c> if is reviewed; otherwise, <c>false</c>.</value>
        public bool isReviewed { get; set; }
        public long categoryId { get; set; }
        [MaxLength(1024)]
        public String categoryLevel { get; set; }
        public int cityId { get; set; }
        [MaxLength(512)]
        public String cityLevel { get; set; }
        /// <summary>
        /// 封面图
        /// </summary>
        /// <value>The main conver image.</value>
        public String mainConverImage { get; set; }
        /// <summary>
        /// 封面图地址，多个用,隔开
        /// </summary>
        /// <value>The cover images.</value>
        [MaxLength(1024)]
        public String coverImages { get; set; }
        /// <summary>
        /// 联系人
        /// </summary>
        /// <value>The name of the contact.</value>
        [MaxLength(20)]
        public String contactName { get; set; }
        /// <summary>
        /// 联系电话
        /// </summary>
        /// <value>The contact phone.</value>
        [MaxLength(20)]
        public String contactPhone { get; set; }
    }
}
