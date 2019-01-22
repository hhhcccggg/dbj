using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    /// <summary>
    /// 用户资产
    /// </summary>
    [Table("core_userAssets")]
    public class UserAsset:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 用户金币余额
        /// </summary>
        /// <value>The coins.</value>
        public long coins { get; set; }
        /// <summary>
        /// 累积获得的金币总数
        /// </summary>
        /// <value>The total coins.</value>
		public long totalCoins { get; set; }
        /// <summary>
        /// 废弃
        /// 用户人民币余额，单位分
        /// </summary>
        /// <value>The remain balance.</value>
        public long remainBalance { get; set; }
        /// <summary>
        /// Gets or sets the user identifier.
        /// </summary>
        /// <value>The user identifier.</value>
        public long userId { get; set; }
    }
}
