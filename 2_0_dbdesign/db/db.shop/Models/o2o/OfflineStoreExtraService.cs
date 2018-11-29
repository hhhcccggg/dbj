using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.o2o
{
    /// <summary>
    /// 门店其他服务，比如：免费停车、免费WIFI等等
    /// </summary>
    [Table("o2o_offlineStoreExtraServices")]
    public class OfflineStoreExtraService:db.common.BaseModelWithTime<long>
    {


    
        public long offlineStoreId { get; set; }
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
