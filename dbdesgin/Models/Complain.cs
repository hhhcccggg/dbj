using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    [Table("core_complains")]
    public class Complain : BaseModelWithTime<long>
    {
        /// <summary>
        /// 0：默认1：匿名2：七牛
        /// 如果为1或者2，fromUserId=0
        /// </summary>
        /// <value>From type identifier.</value>
        public int fromTypeId { get; set; }
        /// <summary>
        /// 举报者
        /// </summary>
        /// <value>From user identifier.</value>
        public long fromUserId { get; set; }
        /// <summary>
        /// 被举报的资源Id
        /// </summary>
        /// <value>To res identifier.</value>
        public long toResId { get; set; }
        /// <summary>
        /// 被举报的资源类型0：用户1：视频2：直播
        /// </summary>
        /// <value>To res type identifier.</value>
        public long toResTypeId { get; set; }
        /// <summary>
        /// 举报原因
        /// </summary>
        /// <value>The reason identifier.</value>
        public long reasonId { get; set; }
        /// <summary>
        /// 举报资源
        /// </summary>
        /// <value>The description.</value>
        [MaxLength(512)]
        public String description { get; set; }
        /// <summary>
        /// 截图快照等网络资源
        /// </summary>
        /// <value>The snapshot URL.</value>
        [MaxLength(512)]
        public String snapshotUrl { get; set; }
    }
}
