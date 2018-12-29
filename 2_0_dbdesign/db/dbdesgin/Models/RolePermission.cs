using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    [Table("core_rolePermissions")]
    public class RolePermission:BaseModel<long>
    {
        [Required]
        [MaxLength(50)]
        public string roleName { get; set; }
        [Required]
        [MaxLength(50)]
        public string permissionName { get; set; }
        /// <summary>
        /// 租户ID
        /// 如果为空，则是系统账号
        /// </summary>
        /// <value>The tenant identifier.</value>
        public long? tenantId { get; set; }
    }
}
