using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    /// <summary>
    /// 用户金币明细
    /// </summary>
    [Table("core_userCoinDetails")]
    public class UserCoinDetail:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 标题
        /// </summary>
        /// <value>The title.</value>
        [MaxLength(512)]
        [Required]
        public string title { get; set; }
        /// <summary>
        /// 金币数
        /// </summary>
        /// <value>The number.</value>
        public int num { get; set; }
        /// <summary>
        /// 附加数据
        /// </summary>
        /// <value>The extra data.</value>
        [MaxLength(1024)]
        public String extraData { get; set; }
        /// <summary>
        /// TASK:任务;PAY:充值;INCOME:收益;OTHER:其他;ENCASH:提现
        /// </summary>
        /// <value>The type.</value>
        [Required]
        [MaxLength(20)]
        public String type { get; set; }
        /// <summary>
        /// Gets or sets the user identifier.
        /// </summary>
        /// <value>The user identifier.</value>
        public long userId { get; set; }
        /// <summary>
        /// 状态
        /// SUCCESS:成功；FAILED：失败; PROCESSING：处理中
        /// </summary>
        /// <value>The status.</value>
        [Required]
        [MaxLength(20)]
        public String status { get; set; }
        /// <summary>
        /// 状态原因说明
        /// </summary>
        /// <value>The status message.</value>
        [MaxLength(1024)]
        public String statusMsg { get; set; }
        /// <summary>
        /// 微信或者支付宝充值流水号，唯一，不能重复
        /// </summary>
        /// <value>The trade no.</value>
        [MaxLength(128)]
        public String tradeNo { get; set; }
        /// <summary>
        /// 交易类型WECHAT：微信；ALIPAY：支付宝
        /// </summary>
        /// <value>The type of the trade.</value>
        [MaxLength(50)]
        public String tradeType { get; set; }
    }
}
