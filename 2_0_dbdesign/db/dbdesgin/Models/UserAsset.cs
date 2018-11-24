using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models
{
    [Table("core_userAssets")]
    public class UserAsset:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 用户金币
        /// </summary>
        /// <value>The coins.</value>
        public long coins { get; set; }
        /// <summary>
        /// 用户余额
        /// </summary>
        /// <value>The remain balance.</value>
        public long remainBalance { get; set; }
    }
}
