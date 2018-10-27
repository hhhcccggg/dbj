using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    [Table("core_complainReasons")]
    public class ComplainReason:BaseModel<long>
    {
        [MaxLength(256)]
        public string title { get; set; }
        [MaxLength(512)]
        public string description { get; set; }
        /// <summary>
        /// 0：用户1：视频2：直播
        /// </summary>
        /// <value>The type.</value>
        public int type { get; set; }
        public bool isOpen { get; set; }
    }
}
