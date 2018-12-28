using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    [Table("core_userRoles")]
    public class UserRole:BaseModel<long>
    {
        [Required]
        public long userId { get; set; }
        [Required]
        [MaxLength(50)]
        public string roleName { get; set; }
        /// <summary>
        /// 租户ID
        /// 如果为空，则是系统账号
        /// </summary>
        /// <value>The tenant identifier.</value>
        public long? tenantId { get; set; }
    }
}
