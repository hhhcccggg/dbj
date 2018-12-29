using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    [Table("core_roles")]
    public class Role
    {
        [Required]
        [MaxLength(50)]
        [Key]
        public string name { get; set; }
        [MaxLength(512)]
        public string description { get; set; } 
        /// <summary>
        /// 租户ID
        /// 如果为空，则是系统账号
        /// </summary>
        /// <value>The tenant identifier.</value>
        public long? tenantId { get; set; }
    }
}
