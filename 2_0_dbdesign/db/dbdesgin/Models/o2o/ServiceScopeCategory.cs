using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models.o2o
{
	/// <summary>
    /// 线下门店服务范围
    /// </summary>
    [Table("o2o_serviceScopeCategories")]
    public class ServiceScopeCategory : db.common.BaseModelWithTime<long>
    {
        [Required]
        [MaxLength(50)]
        public string title { get; set; }
        [Required]
        [MaxLength(512)]
        public string description { get; set; }
        [Required]
        [MaxLength(512)]
        public string iconUrl { get; set; }
        public int orderIndex { get; set; }
        /// <summary>
        /// 0:正常1：下线
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
    }
}
