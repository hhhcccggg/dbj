using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

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
    }
}
