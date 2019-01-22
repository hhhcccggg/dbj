using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models
{
    /// <summary>
    /// 用户邀请好友
    /// </summary>
	[Table("core_userInvitations")]
	public class UserInvitation:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 邀请发起人
        /// </summary>
        /// <value>The initiator user identifier.</value>
		public long initiatorUserId { get; set; }
        /// <summary>
        /// 接受邀请的人
        /// </summary>
        /// <value>The received user identifier.</value>
		public long? receivedUserId { get; set; }
        /// <summary>
		/// 状态
		/// NONE
		/// REG:已注册
		/// PETS:已添加宠物
		/// 当前只关注PETS状态，或者根据需要调整扩展
        /// </summary>
        /// <value>The state.</value>
		[MaxLength(128)]
        [Required]
		public String state { get; set; }
        /// <summary>
        /// 消息
		/// 例如：获取奖励50金币
        /// </summary>
        /// <value>The message.</value>
		[MaxLength(512)]
		public string message { get; set; }
    }
}
