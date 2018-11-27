using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models.o2o
{
    /// <summary>
    /// 线下门店服务范围
    /// </summary>
    [Table("o2o_offlineStoreServiceScopes")]
    public class OfflineStoreServiceScope
    {
        /// <summary>
        /// 就是商户ID
        /// </summary>
        /// <value>The offline store identifier.</value>
        [Key]
        public long offlineStoreId { get; set; }
        /// <summary>
        /// 分类ID
        /// </summary>
        /// <value>The service scope identifier.</value>
        [Key]
        public long serviceScopeId { get; set; }
        [MaxLength(1024)]
        public String notes { get; set; }
        /// <summary>
        /// 0:正常1:下线
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
    }
}
