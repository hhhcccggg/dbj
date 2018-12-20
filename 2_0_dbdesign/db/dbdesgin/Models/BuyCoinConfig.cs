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
        /// <summary>
        /// 平台类型：IOS:苹果，ANDROID：安卓, WECHATMINAPP：微信小程序
        /// </summary>
        /// <value>The type.</value>
        [Required]
        [MaxLength(20)]
        public string type { get; set; }
        /// <summary>
        /// 产品ID，针对苹果内购有效
        /// </summary>
        /// <value>The product identifier.</value>
        [MaxLength(128)]
        public string productId { get; set; }
    }
}
