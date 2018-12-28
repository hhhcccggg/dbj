using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    /// <summary>
    /// 评论统一表
    /// </summary>
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
        /// 被评论目标资源ID，比如视频ID、商品ID等
        /// </summary>
        /// <value>The resource identifier.</value>
        public long resourceOwnerId { get; set; }
        /// <summary>
        /// 0:针对视频评论1:针对下线商家服务评论2：针对商家产品的评论
        /// </summary>
        /// <value>The resource type identifier.</value>
        [Required]
        public int resourceTypeId { get; set; }
        /// <summary>
        /// 评分
        /// </summary>
        /// <value>The rate.</value>
        public float rate { get; set; }
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
    /// <summary>
    /// 评论关联的媒体资源
    /// </summary>
    [Table("core_comment_extraDatas")]
    public class CommentExtraData:db.common.BaseModelWithTime<long> {
        /// <summary>
        /// 资源类型，VIDEO：视频,IMAGE:图片
        /// 更多类型待扩展
        /// </summary>
        /// <value>The type.</value>
        [Required]
        [MaxLength(50)]
        public string type { get; set; }
        /// <summary>
        /// 评论ID
        /// </summary>
        /// <value>The comment identifier.</value>
        public long commentId { get; set; }
        /// <summary>
        /// 资源的ID，比如视频ID
        /// 更多类型待扩展
        /// </summary>
        /// <value>The data identifier.</value>
        public long dataId { get; set; }
        /// <summary>
        /// 媒体资源内容，如果dataId==0，则读取此字段的值
        /// </summary>
        /// <value>The content of the data.</value>
        public String dataContent { get; set; }
    }
}
