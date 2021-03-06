﻿using System;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;
namespace db.shop.Models.o2o
{
    /// <summary>
    /// 营业时间
    /// </summary>
    [Table("o2o_offlineStoreOpeningHours")]
    public class OfflineStoreOpeningHour:BaseModelWithTime<long>
    {
        /// <summary>
        /// 周几：1|2|3|4|5|6|7
        /// </summary>
        /// <value>The day.</value>
        public int day { get; set; }
        public long storeId { get; set; }
        /// <summary>
        /// 营业时间，单位秒
        /// </summary>
        /// <value>The open time.</value>
        public int openTime { get; set; }
        /// <summary>
        /// 下班时间，单位秒
        /// </summary>
        /// <value>The close time.</value>
        public int closeTime { get; set; }
    }
}
