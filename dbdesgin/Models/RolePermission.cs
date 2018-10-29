using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

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
    }
}
