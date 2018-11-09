using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    /// <summary>
    /// 消息中心
    /// </summary>
    [Table("core_messages")]
    public class Message:BaseModelWithTime<long>
    {
        /// <summary>
        /// 消息创建者
        /// </summary>
        public long creatorUserId { get; set; }
        /// <summary>
        /// 消息文本内容
        /// </summary>
        [Required]
        [MaxLength(1024)]
        public string msgContent { get; set; }
        /// <summary>
        /// 消息数据内容，JSON格式
        /// </summary>
        /// <value>The content of the data.</value>
        [MaxLength(2048)]
        public string dataContent { get; set; }
        /// <summary>
        /// 消息关联的链接地址
        /// </summary>
        /// <value>The reference URL.</value>
        [MaxLength(1024)]
        public string refUrl { get; set; }
        /// <summary>
        /// 消息类型0:系统消息,1:点赞类2:粉丝类3:评论
        /// </summary>
        public int messageType { get; set; }
    }
}
