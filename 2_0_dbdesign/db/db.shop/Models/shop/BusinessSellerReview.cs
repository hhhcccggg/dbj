using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;
namespace db.shop.Models.shop
{
    /// <summary>
    /// 认证资料&审核情况
    /// </summary>
    [Table("o2o_offlineStoreReviews")]
    public class BusinessSellerReview:BaseModelWithTime<long>
    {
        [Required]
        [MaxLength(50)]
        public String identifyId { get; set; }
        [Required]
        [MaxLength(30)]
        public String title { get; set; }
        [Required]
        [MaxLength(1024)]
        public String reviewData { get; set; }
        /// <summary>
        /// 状态:0正常1：审核中2：拒接
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
        [MaxLength(512)]
        public String rejectMsg { get; set; }
        /// <summary>
        /// 商户(线下门店ID)
        /// </summary>
        /// <value>The offline store identifier.</value>
        public long businessSellerId { get; set; }
    }
}
