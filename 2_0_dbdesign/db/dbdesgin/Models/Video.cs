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
        /// 0:正常1:审核中2:未通过审核3:需要人工审核4:下架
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
        public bool isManualRecommend { get; set; }
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
        /// <summary>
        /// 收到小费(打赏次数)
        /// </summary>
        /// <value>The tip count.</value>
        public long tipCount { get; set; }

        #region 20181217 支持评论里带视频
        /// <summary>
        /// 视频的类型>>USER：用户发布的视频SHOPCOMMENT：商品或者服务评论发布的视频
        /// </summary>
        /// <value>The type.</value>
        [MaxLength(50)]
        [Required]
        public string type { get; set; }
        #endregion

    }
}
