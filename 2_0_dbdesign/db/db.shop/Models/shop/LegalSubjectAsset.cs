using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 商户资产
    /// </summary>
    [Table("shop_legalSubjectAssets")]
    public class LegalSubjectAsset:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 余额
        /// 单位分
        /// </summary>
        /// <value>The remain balance.</value>
        public long remainBalance { get; set; }
        /// <summary>
        /// 锁定的余额
        /// 单位分
        /// </summary>
        /// <value>The locked balance.</value>
        public long lockedBalance { get; set; }
        /// <summary>
        /// 商户
        /// </summary>
        /// <value>The legal subject identifier.</value>
        public long legalSubjectId { get; set; }
    }
}
