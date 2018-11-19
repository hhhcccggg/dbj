using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models.shop
{
    /// <summary>
    /// 物流模板
    /// </summary>
    [Table("shop_deliveryTemplates")]
    public class DeliveryTemplate : db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 卖家ID
        /// </summary>
        /// <value>The seller identifier.</value>
        public long sellerId { get; set; }
        [Required]
        [MaxLength(30)]
        public string name { get; set; }
        /// <summary>
        /// 计费类型0:计件1：重量kg
        /// </summary>
        /// <value>The type of the bill.</value>
        public int billType { get; set; }
        /// <summary>
        /// 配送范围
        /// </summary>
        /// <value>The delivery template scope identifier.</value>
        public long deliveryTemplateScopeId { get; set; }
    }
}
