using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    [Table("core_videos")]
    public class Video : BaseModelWithTime<long>
    {
        [Required]
        [MaxLength(512)]
        public string title { get; set; }
        [Required]
        [MaxLength(512)]
        public string coverImageUrl { get; set; }
        public float coverImageWidth { get; set; }
        public float coverImageHeight { get; set; }
        [Required]
        [MaxLength(512)]
        public string firstFrameUrl { get; set; }
        public float firstFrameWidth { get; set; }
        public float firstFrameHeight { get; set; }
        [Required]
        [MaxLength(512)]
        public string videoUrl { get; set; }
        public string linkPets { get; set; }
        public string tags { get; set; }
        public float? longitude { get; set; }
        public float? latitude { get; set; }
        public string address { get; set; }
        public bool isHiddenLocation { get; set; }
        /// <summary>
        /// 0:正常1:审核中2:下架3:需要人工审核
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
        [MaxLength(512)]
        public string rejectMsg { get; set; }
        /// <summary>
        /// 审核人员
        /// </summary>
        /// <value>The review user identifier.</value>
        public long? reviewUserId { get; set; }
        /// <summary>
        /// 审核操作的时间
        /// </summary>
        /// <value>The review time.</value>
        [Column("reviewTime", TypeName = "timestamp")]
        public DateTime? reviewTime { get; set; }
        /// <summary>
        /// 推荐指数
        /// </summary>
        /// <value>The index of the recommend.</value>
        public int recommendIndex { get; set; }
        // 是否手动推荐,当前未启用此字段
        public bool isManualRecommend {get;set;}
        public long playCount { get; set; }
        public long commentCount { get; set; }
        public long heartCount { get; set; }
        public long shareCount { get; set; }
        [Required]
        public long userId { get; set; }
        public long? musicId { get; set; }
        /// <summary>
        /// 关联的商品数
        /// </summary>
        /// <value>The link product count.</value>
        public int linkProductCount { get; set; }
        /// <summary>
        /// 投诉举报次数
        /// </summary>
        /// <value>The complain count.</value>
        public int complainCount { get; set; }
    }
}
