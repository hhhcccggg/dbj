using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.o2o
{
    /// <summary>
    /// 门店员工，或者代言人
    /// </summary>
    [Table("o2o_offlineStoreStaffs")]
    public class OfflineStoreStaff: db.common.BaseModelWithTime<long>
    {
        public long offlineStoreId { get; set; }
        public long userId { get; set; }
        /// <summary>
        /// 是否为代言人
        /// </summary>
        /// <value><c>true</c> if is super star; otherwise, <c>false</c>.</value>
        public bool isSuperStar { get; set; }
    }
}
