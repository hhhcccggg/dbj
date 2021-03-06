﻿using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    /// <summary>
    /// 数据每日增长情况
    /// </summary>
    [Table("core_dailyIncreaseAnalysises")]
    public class DailyIncreaseAnalysis : BaseModelWithTime<long>
    {
        /// <summary>
        /// 新增的用户数
        /// </summary>
        /// <value>The new users.</value>
        public long newUsers { get; set; }
        /// <summary>
        /// 新增的视频数
        /// </summary>
        /// <value>The new videos.</value>
        public long newVideos { get; set; }
        /// <summary>
        /// 新增的订单数
        /// 当前忽略此字段
        /// </summary>
        /// <value>The new orders.</value>
        public long newOrders { get; set; }
        /// <summary>
        /// 日活
        /// </summary>
        /// <value>The per dau.</value>
        public long perDau { get; set; }
        /// <summary>
        /// 月活
        /// </summary>
        /// <value>The per mau.</value>
        public long perMau { get; set; }
        /// <summary>
        /// FAKE:假TRUTH:真
        /// </summary>
        /// <value>The type.</value>
        [Required]
        [MaxLength(20)]
        public String type { get; set; }
    }
}
