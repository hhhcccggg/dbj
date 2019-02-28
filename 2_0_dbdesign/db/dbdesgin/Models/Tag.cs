using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

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
        public bool isHot { get; set; }
        //状态:0:正常,1,停用
        public int status { get; set; }
        /// <summary>
        /// 描述
        /// </summary>
        /// <value>The desc.</value>
        [MaxLength(1024)]
        public string desc { get; set; }
    }
}
