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
        [Key]
        public long offlineStoreId { get; set; }
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
