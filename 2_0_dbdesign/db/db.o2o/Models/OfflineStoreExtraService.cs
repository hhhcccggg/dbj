using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.o2o.Models
{
    /// <summary>
    /// 门店其他服务，比如：免费停车、免费WIFI等等
    /// </summary>
    [Table("o2o_offlineStoreExtraServices")]
    public class OfflineStoreExtraService
    {
        [Key]
        public long offlineStoreId { get; set; }
        [Key]
        public long extraServiceId { get; set; }
        /// <summary>
        /// 0:正常1：非正常
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
        [MaxLength(1024)]
        public String notes { get; set; }
    }
}
