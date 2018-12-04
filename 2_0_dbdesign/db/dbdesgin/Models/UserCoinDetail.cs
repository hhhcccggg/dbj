using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models
{
    /// <summary>
    /// 用户金币明细
    /// </summary>
    [Table("core_userCoinDetails")]
    public class UserCoinDetail:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 标题
        /// </summary>
        /// <value>The title.</value>
        [MaxLength(512)]
        [Required]
        public string title { get; set; }
        /// <summary>
        /// 金币数
        /// </summary>
        /// <value>The number.</value>
        public int num { get; set; }
        /// <summary>
        /// 附加数据
        /// </summary>
        /// <value>The extra data.</value>
        [MaxLength(1024)]
        public String extraData { get; set; }
        /// <summary>
        /// 收入来源，类型待定义
        /// </summary>
        /// <value>The type of the income.</value>
        public int incomeType { get; set; }
        /// <summary>
        /// 金币支出限制
        /// </summary>
        /// <value>The pay limit.</value>
        public int payLimit { get; set; }
    }
}
