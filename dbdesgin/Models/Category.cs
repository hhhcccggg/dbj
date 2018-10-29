using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    [Table("core_categories")]
    public class Category : BaseModelWithTime<long>
    {
        [Required]
        [MaxLength(20)]
        public string name { get; set; }
        // 父节点,默认传0
        public long parentId { get; set; }
        // 0:宠物分类
        public int type { get; set; }
        // 分类创建者，如果是系统内置分类，此字段保持为null
        public long? userId { get; set; }
    }
}
    