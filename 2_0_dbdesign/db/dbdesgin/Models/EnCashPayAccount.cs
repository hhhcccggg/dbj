using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    /// <summary>
    /// 提现第三方支付账号
    /// </summary>
    [Table("core_enCashAccounts")]
    public class EnCashPayAccount:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 账号类型：ALIPAY:支付宝账号;WECHAT:微信账号,更多扩展
        /// </summary>
        /// <value>The type.</value>
        [Required]
        [MaxLength(20)]
        public string type { get; set; }
        /// <summary>
        /// 账号
        /// </summary>
        /// <value>The unique identifier.</value>
        [Required]
        [MaxLength(128)]
        public string uniqueId { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        /// <value>The name.</value>
        [Required]
        [MaxLength(50)]
        public string name { get; set; }
        /// <summary>
        /// 头像
        /// </summary>
        /// <value>The avatar URL.</value>
        [MaxLength(512)]
        public string avatarUrl { get; set; }
        /// <summary>
        /// 访问token，可以根据token获取用户详情
        /// </summary>
        /// <value>The access token.</value>
        [MaxLength(1024)]
        public string accessToken { get; set; }
        /// <summary>
        /// 过期时间
        /// </summary>
        /// <value>The expire in.</value>
        public long expireIn { get; set; }
        /// <summary>
        /// 用户
        /// </summary>
        /// <value>The user identifier.</value>
        public long userId { get; set; }
        /// <summary>
        /// 是否锁定，锁定以后不可用
        /// </summary>
        /// <value><c>true</c> if is locked; otherwise, <c>false</c>.</value>
        public bool isLocked { get; set; }
    }
}
