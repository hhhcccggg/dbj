using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    [Table("core_tags")]
    public class Tag : BaseModelWithTime<long>
    {
        [Required]
        [MaxLength(20)]
        public string name { get; set; }
        // 推荐指数，越高靠前
        public int recommendIndex { get; set; }
        // 相关联的资源数
        public long resNumber { get; set; }
    }
}
