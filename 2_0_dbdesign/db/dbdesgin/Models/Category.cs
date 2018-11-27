using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

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
        [MaxLength(512)]
        public String iconUrl { get; set; }
        /// <summary>
        /// 排序
        /// </summary>
        /// <value>The index of the order.</value>
        public int orderIndex { get; set; }
        /// <summary>
        /// 0:正常1：下线
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
    }
}
    