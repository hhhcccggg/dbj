using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.o2o
{
    /// <summary>
    /// 代言人
    /// </summary>
    [Table("o2o_offlineStoreStaffs")]
    public class OfflineStoreStaff: db.common.BaseModelWithTime<long>
    {
        public long storeId { get; set; }
        public long userId { get; set; }
    }
}
