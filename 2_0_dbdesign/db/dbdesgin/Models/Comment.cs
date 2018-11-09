using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    [Table("core_comments")]
    public class Comment : BaseModelWithTime<long>
    {
        [Required]
        public long userId { get; set; }
        /// <summary>
        /// 是否为楼主
        /// </summary>
        /// <value><c>true</c> if is owner; otherwise, <c>false</c>.</value>
        public bool isOwner { get; set; }
        /// <summary>
        /// 评论内容，如果违规，用******提示
        /// </summary>
        /// <value>The content text.</value>
        [Required]
        [MaxLength(1024)]
        public String contentTxt { get; set; }
        public long refCommentId { get; set; }
        public long heartCount { get; set; }
        /// <summary>
        /// 资源ID，比如视频ID
        /// </summary>
        /// <value>The resource identifier.</value>
        public long resourceOwnerId { get; set; }
        [Required]
        public int resourceTypeId { get; set; }
        /// <summary>
        /// 审核状态pass:通过reviewing:审核中block:拒绝review:需要人工审核
        /// </summary>
        /// <value>The review status.</value>
        [MaxLength(20)]
        public string reviewStatus { get; set; }
        /// <summary>
        /// 审核结果
        /// </summary>
        /// <value>The reviewed result.</value>
        [MaxLength(1024)]
        public String reviewedResult { get; set; }
        /// <summary>
        /// 原始的评论内容
        /// </summary>
        /// <value>The origin content text.</value>
        [MaxLength(1024)]
        public string originContentTxt { get; set; }
    }
}
