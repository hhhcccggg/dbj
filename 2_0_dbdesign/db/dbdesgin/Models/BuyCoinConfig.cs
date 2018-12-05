using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    /// <summary>
    /// 可选充值金币配置列表
    /// </summary>
    [Table("core_basic_buyCoinConfigs")]
    public class BuyCoinConfig : db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 金币
        /// </summary>
        /// <value>The coins.</value>
        public int coins { get; set; }
        /// <summary>
        /// 对应的金币价值多少人民币，单位分
        /// </summary>
        /// <value>The rmbs.</value>
        public int rmbs { get; set; }
        /// <summary>
        /// 标题
        /// </summary>
        /// <value>The title.</value>
        [Required]
        [MaxLength(30)]
        public string title { get; set; }
        /// <summary>
        /// 排序
        /// </summary>
        /// <value>The index of the order.</value>
        public int orderIndex { get; set; }
    }
}
