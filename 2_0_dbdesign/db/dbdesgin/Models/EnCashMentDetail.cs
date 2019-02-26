using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    /// <summary>
    /// 提现详细进度
    /// </summary>
    [Table("core_enCashMentDetails")]
    public class EnCashMentDetail:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 用户
        /// </summary>
        /// <value>The user identifier.</value>
        public long userId { get; set; }
        /// <summary>
        /// 提现的金币
        /// </summary>
        /// <value>The coins.</value>
        public int coins { get; set; }
        /// <summary>
        /// 提现的金额，单位分
        /// </summary>
        /// <value>The rmbs.</value>
        public int rmbs { get; set; }
        /// <summary>
        /// 提现到的账户ID
        /// </summary>
        /// <value>The pay account identifier.</value>
        public long payAccountId { get; set; }
        /// <summary>
        /// 账号类型：PAY：第三方支付账号（微信或者支付宝）BANK：银行卡
        /// </summary>
        /// <value>The type of the pay account.</value>
        [Required]
        [MaxLength(30)]
        public String payAccountType { get; set; }
        /// <summary>
        /// 该次提现是否已审核通过，只有审核通过以后才可以进行提现操作
        /// </summary>
        /// <value><c>true</c> if is allowed en cash; otherwise, <c>false</c>.</value>
        public bool isAllowedEnCash { get; set; }
        /// <summary>
        /// 提现状态REVIEWING：审核中;SUCCESS：成功；PROCESSING：处理中；FAILED：失败 REVIEWED：审核成功
        /// </summary>
        /// <value>The status.</value>
        [Required]
        [MaxLength(30)]
        public string status { get; set; }
        /// <summary>
        /// 状态||交易结果的备注
        /// </summary>
        /// <value>The result reason.</value>
        [MaxLength(512)]
        public string resultReason { get; set; }
        /// <summary>
        /// 额外的数据
        /// </summary>
        /// <value>The extra data.</value>
        public String extraData { get; set; }
        /// <summary>
        /// 交易流水号，唯一，不能重复
        /// </summary>
        /// <value>The trade no.</value>
        public String tradeNo { get; set; }
    }
}
