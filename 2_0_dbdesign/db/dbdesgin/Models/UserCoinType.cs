using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    /// <summary>
    /// 分类型存储用户金币总额
    /// </summary>
    [Table("core_userCoinTypes")]
    public class UserCoinType : db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// TASK:任务;PAY:充值;INCOME:收益;OTHER:其他
        /// 更多类型以后扩展
        /// </summary>
        /// <value>The type.</value>
        [Required]
        [MaxLength(20)]
        public String type { get; set; }
        /// <summary>
        /// 金币总额
        /// </summary>
        /// <value>The coins.</value>
        public long coins { get; set; }
        /// <summary>
        /// Gets or sets the user identifier.
        /// </summary>
        /// <value>The user identifier.</value>
        public long userId { get; set; }
        /// <summary>
        /// 锁住的金币，锁住的金币不可用
        /// 比如：金币在提现中等这样的场景
        /// </summary>
        /// <value>The locked coins.</value>
        public long lockedCoins { get; set; }
    }
}
