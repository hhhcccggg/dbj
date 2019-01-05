using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 主体需要审核的资料
    /// </summary>
    [Table("shop_legalSubjectReviews")]
    public class LegalSubjectReview:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 需要审核的主体对象
        /// ID:身份证
        /// CORP:营业执照
        /// 更多扩展..
        /// </summary>
        /// <value>The identify identifier.</value>
        [Required]
        [MaxLength(128)]
        public String identifyId { get; set; }
        [Required]
        [MaxLength(128)]
        public String title { get; set; }
        /// <summary>
        /// 主体对象的编码
        /// </summary>
        /// <value>The key identifier.</value>
        [MaxLength(1024)]
        public String keyId { get; set; }
        /// <summary>
        /// 多个内容用,隔开
        /// 相同类型的数据
        /// </summary>
        /// <value>The review data.</value>
        [Required]
        public String reviewData { get; set; }
        /// <summary>
        /// 状态:0正常1：审核中2：拒接
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
        [MaxLength(512)]
        public String rejectMsg { get; set; }
        /// <summary>
        /// 商户Id
        /// </summary>
        /// <value>The offline store identifier.</value>
        [Required]
        public long legalSubjectId { get; set; }
    }
}
