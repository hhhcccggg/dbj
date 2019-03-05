using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    /// <summary>
    /// 当前所有平台用户都在此表
    /// TODO 未来考虑拆分
    /// </summary>
    [Table("core_users")]
    public class User : BaseModelWithTime<long>
    {
        [Required]
        [MaxLength(110)]
        [Column("username")]
        public string UserName { get; set; }
        /// <summary>
        /// 真实姓名
        /// </summary>
        /// <value>The full name.</value>
        [MaxLength(128)]
        [Column("fullName")]
        public String FullName { get; set; }
        [MaxLength(20)]
        public string nickName { get; set; } 
        [MaxLength(512)]
        public string avatarUrl { get; set; }
        [MaxLength(512)]
        [Column("password")]
        public string Password { get; set; }
        [MaxLength(20)]
        [Column("phone")]
        public string Phone { get; set; }
        [MaxLength(50)]
        [Column("email")]
        public string Email { get; set; }
        /// <summary>
        /// 生日出生年月
        /// </summary>
        /// <value>The birthday.</value>
        [Column("birthday", TypeName = "timestamp")]
        public DateTime? birthday { get; set; }
        /// <summary>
        /// 0：未知1：男2：女3：保密
        /// </summary>
        /// <value>The sex.</value>
        public int sex { get; set; }
        /// <summary>
        /// 账号是否锁定
        /// </summary>
        /// <value><c>true</c> if is locked; otherwise, <c>false</c>.</value>
        [Column("isLocked")]
        public bool IsLocked { get; set; }
        /// <summary>
        /// 电子邮件是否验证
        /// </summary>
        /// <value><c>true</c> if is email verification; otherwise, <c>false</c>.</value>
        [Column("isEmailVerification")]
        public bool IsEmailVerification { get; set; }
        /// <summary>
        /// 手机号是否验证
        /// </summary>
        /// <value><c>true</c> if is phone verification; otherwise, <c>false</c>.</value>
        [Column("isPhoneVerification")]
        public bool IsPhoneVerification { get; set; }
        /// <summary>
        /// 是否有权限直播
        /// </summary>
        /// <value><c>true</c> if is living open; otherwise, <c>false</c>.</value>
        public bool isLivingOpen { get; set; }
        /// <summary>
        /// 是否有观看直播的权限
        /// </summary>
        /// <value><c>true</c> if is living watch; otherwise, <c>false</c>.</value>
        public bool isLivingWatch { get; set; }
        /// <summary>
        /// 是否正在直播
        /// </summary>
        /// <value><c>true</c> if is living; otherwise, <c>false</c>.</value>
        public bool isLiving { get; set; }
        /// <summary>
        /// 直播的ID
        /// </summary>
        /// <value>The living identifier.</value>
        public long livingId { get; set; }
        /// <summary>
        /// 获取的赞
        /// </summary>
        /// <value>The total hearts.</value>
        public long totalHearts { get; set; }
        public long totalFans { get; set; }
        /// <summary>
        /// 我关注的
        /// </summary>
        /// <value>The total my focuses.</value>
        public long totalMyFocuses { get; set; }
        /// <summary>
        /// 是否为超级管理员
        /// </summary>
        /// <value><c>true</c> if is super; otherwise, <c>false</c>.</value>
        public bool isSuper { get; set; }

        /// <summary>
        /// 是否认证
        /// </summary>
        /// <value><c>true</c> if is reviewed; otherwise, <c>false</c>.</value>
        public bool isReviewed { get; set; }
        /// <summary>
        /// 登录方式1:手机号2:账号和密码4:微信8:QQ16:微博
        /// </summary>
        /// <value>The type of the login.</value>
        public int loginType { get; set; }
        /// <summary>
        /// 第三方用户唯一ID编码
        /// </summary>
        /// <value>The third open identifier.</value>
        [MaxLength(1024)]
        public string thirdOpenId { get; set; }
        public float? longitude { get; set; }
        public float? latitude { get; set; }
        [MaxLength(512)]
        public string address { get; set; }
        /// <summary>
        /// 投诉举报次数
        /// </summary>
        /// <value>The complain count.</value>
        public int complainCount { get; set; }
        /// <summary>
        /// 是否是管理平台用户
        /// </summary>
        /// <value><c>true</c> if is manager; otherwise, <c>false</c>.</value>
        public bool isManager { get; set; }
        /// <summary>
        /// 职业类型
        /// </summary>
        /// <value>The occupation identifier.</value>
        public int occupationId { get; set; }
        /// <summary>
        /// 情感/婚姻状态
        /// </summary>
        /// <value>The love status.</value>
        public int loveStatusId { get; set; }
        /// <summary>
        /// 环信账号
        /// </summary>
        /// <value>The name of the hx user.</value>
        [MaxLength(50)]
        public string hxUserName { get; set; }
        /// <summary>
        /// 环信密码
        /// </summary>
        /// <value>The hx pwd.</value>
        [MaxLength(512)]
        public string hxPwd { get; set; }
        /// <summary>
        /// 用户类型
        /// NORMAL:普通用户，直接是产品的用户，来自于APP、H5、小程序等渠道
        /// PLATFORM: 平台用户，主要是自由平台的用户
        /// BUSINESS: 商家用户
        /// </summary>
        /// <value>The type.</value>
        [MaxLength(50)]
        public string type { get; set; }
        /// <summary>
        /// 租户ID
        /// 如果为空，则是平台账号
        /// </summary>
        /// <value>The tenant identifier.</value>
        public long? tenantId { get; set; }
        /// <summary>
        /// 谁推荐用户注册
        /// </summary>
        /// <value>The recommend user identifier.</value>
		public long? recommendUserId { get; set; }
        /// <summary>
        /// 备注信息
        /// </summary>
        /// <value>The notes.</value>
        [MaxLength(128)]
        public String notes { get; set; }
        /// <summary>
        /// 个性签名
        /// </summary>
        /// <value>The signature.</value>
        [MaxLength(128)]
        public String signature { get; set; }
    }
}
