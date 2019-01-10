using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models
{
    /// <summary>
    /// Ad banner.
    /// </summary>
    [Table("core_adBanners")]
    public class AdBanner:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 区分不同类型
        /// 不同地方的Banner
        /// MINIAPP_HOME:微信小程序首页
        /// APP_O2O_HOME:app周边首页
        /// </summary>
        /// <value>The type.</value>
        [Required]
        [MaxLength(128)]
        public String type { get; set; }
        /// <summary>
        /// 平台
        /// IOS:苹果
        /// ANDROID:安卓
        /// ALL:所有
        /// </summary>
        /// <value>The platform.</value>
        [Required]
        [MaxLength(128)]
        public String platform { get; set; }
        /// <summary>
        /// 名称
        /// </summary>
        /// <value>The title.</value>
        [Required]
        [MaxLength(128)]
        public String title { get; set; }
        /// <summary>
        /// 图片地址
        /// </summary>
        /// <value>The image URL.</value>
        [Required]
        [MaxLength(1024)]
        public String imageUrl { get; set; }
        /// <summary>
        /// 关联的H5网页
        /// </summary>
        /// <value>The reference URL.</value>
        [MaxLength(2048)]
        public string refUrl { get; set; }
        /// <summary>
        /// 状态
        /// ONLINE:上线
        /// OFFLINE:下线
        /// </summary>
        /// <value>The state.</value>
        [Required]
        [MaxLength(128)]
        public string state { get; set; }

    }
}
