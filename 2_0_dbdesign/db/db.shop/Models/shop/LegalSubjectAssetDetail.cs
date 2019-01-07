using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 商户余额变动明细
    /// </summary>
    [Table("shop_legalSubjectAssetDetails")]
    public class LegalSubjectAssetDetail:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 标题
        /// </summary>
        /// <value>The title.</value>
        [MaxLength(512)]
        [Required]
        public string title { get; set; }
        /// <summary>
        /// 变动余额
        /// 单位分
        /// </summary>
        /// <value>The number.</value>
        public long changeBalance { get; set; }
        /// <summary>
        /// 当前余额
        /// 单位分
        /// </summary>
        /// <value>The current balance.</value>
        public long currentBalance { get; set; }
        /// <summary>
        /// 附加数据
        /// </summary>
        /// <value>The extra data.</value>
        public String extraData { get; set; }
        /// <summary>
        /// 备注
        /// </summary>
        /// <value>The notes.</value>
        [MaxLength(1024)]
        public String notes { get; set; }
        /// <summary>
        /// 类型
        /// SHOP: 商城收款
        /// </summary>
        /// <value>The type.</value>
        [Required]
        [MaxLength(128)]
        public string type { get; set; }
        /// <summary>
        /// 关联的订单号
        /// </summary>
        /// <value>The order identifier.</value>
        public long? orderId { get; set; }
        /// <summary>
        /// 商户
        /// </summary>
        /// <value>The legal subject identifier.</value>
        public long legalSubjectId { get; set; }
    }
}
