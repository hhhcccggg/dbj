using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.o2o.Models
{
    /// <summary>
    /// 门店员工，或者代言人
    /// </summary>
    [Table("o2o_offlineStoreStaffs")]
    public class OfflineStoreStaff
    {
        [Key]
        public long offlineStoreId { get; set; }
        [Key]
        public long userId { get; set; }
        /// <summary>
        /// 是否为代言人
        /// </summary>
        /// <value><c>true</c> if is super star; otherwise, <c>false</c>.</value>
        public bool isSuperStar { get; set; }
    }
}
