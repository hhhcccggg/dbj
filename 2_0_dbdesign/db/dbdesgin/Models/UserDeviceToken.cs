using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    /// <summary>
    /// 用户推送设备token，当前只是支持单设备
    /// </summary>
    [Table("core_userDeviceTokens")]
    public class UserDeviceToken : BaseModelWithTime<long>
    {
        /// <summary>
        /// 用户ID
        /// </summary>
        /// <value>The user identifier.</value>
        public long userId { get; set; }
        /// <summary>
        /// 设备的token
        /// </summary>
        /// <value>The device token.</value>
        [MaxLength(512)]
        public string deviceToken { get; set; }
        /// <summary>
        /// 设备类型：ios或者android，目前这两种取值
        /// </summary>
        /// <value>The type of the device.</value>
        [MaxLength(20)]
        public string deviceType { get; set; }
        /// <summary>
        /// 设备的名字，可以为空
        /// </summary>
        /// <value>The name of the device.</value>
        [MaxLength(50)]
        public string deviceName { get; set; }
    }
}
