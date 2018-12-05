using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    /// <summary>
    /// 视频打赏详情
    /// </summary>
    [Table("core_video_videoTipDetails")]
    public class VideoTipDetail : db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 被打赏的视频
        /// </summary>
        /// <value>The video identifier.</value>
        public long videoId { get; set; }
        /// <summary>
        /// 打赏视频的用户
        /// </summary>
        /// <value>The user identifier.</value>
        public long userId { get; set; }
        /// <summary>
        /// 打赏的金币
        /// </summary>
        /// <value>The tip coin.</value>
        public long tipCoin { get; set; }
    }
}
