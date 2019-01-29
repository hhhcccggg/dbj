using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    /// <summary>
    /// 资源点赞
    /// </summary>
    [Table("core_hearts")]
    public class Heart : BaseModelWithTime<long>
    {
        /// <summary>
        /// 谁点赞
        /// </summary>
        /// <value>The user identifier.</value>
        public long userId { get; set; }
        /// <summary>
        /// 点赞的对象资源ID，比如视频ID
        /// </summary>
        /// <value>The resource identifier.</value>
        public long resourceOwnerId { get; set; }
        /// <summary>
        /// 0:默认，短视频点赞1:评论2:宠物
        /// </summary>
        /// <value>The resource type identifier.</value>
        [Required]
        public int resourceTypeId { get; set; }
    }
}
    