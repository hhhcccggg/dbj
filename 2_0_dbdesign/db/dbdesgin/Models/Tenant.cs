using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models
{
    /// <summary>
    /// 租户信息
    /// </summary>
    [Table("core_user_tenants")]
    public class Tenant:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 租户名字
        /// </summary>
        /// <value>The name.</value>
        [Required]
        [MaxLength(128)]
        public String name { get; set; }
        /// <summary>
        /// 租户标识
        /// 要保持唯一性
        /// </summary>
        /// <value>The name of the identify.</value>
        [Required]
        [MaxLength(128)]
        public String identifyName { get; set; }
        /// <summary>
        /// 电商系统中的商户ID
        /// 如果是0，则表示没有电商系统关联
        /// </summary>
        /// <value>The legal subject identifier.</value>
        public long legalSubjectId { get; set; }
    }
}
