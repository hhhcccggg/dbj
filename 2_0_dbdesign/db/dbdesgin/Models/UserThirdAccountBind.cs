using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    /// <summary>
    /// 用户第三方账号绑定
    /// </summary>
    [Table("core_userThirdAccountBinds")]
    public class UserThirdAccountBind:BaseModelWithTime<long>
    {
        public long userId {get;set;}
        [MaxLength(1024)]
        public string thirdOpenId { get; set; }
        /// <summary>
        /// 1:手机号2:账号和密码4:微信8:QQ16:微博
        /// 与user表中loginType取值一样
        /// </summary>
        /// <value>The type of the account.</value>
        public int accountType { get; set; }
        /// <summary>
        /// 访问token
        /// </summary>
        [MaxLength(1024)]
        public string accessToken { get; set; }
        /// <summary>
        /// token过期时间
        /// </summary>
        public long exipreIn { get; set; }
        /// <summary>
        /// 昵称
        /// </summary>
        /// <value>The name of the nick.</value>
        [MaxLength(128)]
        public string nickName { get; set; }
    }
}
