using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
	/// <summary>
    /// APP版本管理
    /// </summary>
    [Table("core_appVersions")]
    public class AppVersion:BaseModelWithTime<long>
    {
        /// <summary>
        /// 版本号,同一个平台不能有重复
        /// </summary>
        /// <value>The version.</value>
        [MaxLength(30)]
        public string version { get; set; }
        /// <summary>
        /// 版本号数字，判定是否更新依据此数字，同一个平台不能有重复
        /// </summary>
        /// <value>The version number.</value>
        public int versionNum { get; set; }
        /// <summary>
        /// 标题
        /// </summary>
        /// <value>The title.</value>
        [MaxLength(50)]
        public string title { get; set; }
        /// <summary>
        /// Gets or sets the description.
        /// </summary>
        /// <value>The description.</value>
        [MaxLength(1024)]
        public string description { get; set; }
        /// <summary>
        /// 0:iOS,1:android
        /// </summary>
        /// <value>The platform.</value>
        public int platform { get; set; }
        /// <summary>
        /// 下载链接
        /// </summary>
        /// <value>The download URL.</value>
        [MaxLength(512)]
        public string downloadUrl { get; set; }
        /// <summary>
        /// 升级类型
        /// 0：选择升级1：强制升级
        /// </summary>
        /// <value>The type of the upgrade.</value>
        public int upgradeType { get; set; }
    }
}
