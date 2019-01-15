using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models
{
    /// <summary>
    /// 系统任务
    /// </summary>
    [Table("core_tasks")]
	public class Task:db.common.BaseModelWithTime<String>
    {
        /// <summary>
        /// 任务类型
		/// NEWUSER:新手任务
		/// DAILY:日常任务
		/// INVITATION:邀请任务
        /// </summary>
        /// <value>The type.</value>
        [Required]
		[MaxLength(128)]
		public string type { get; set; }
        /// <summary>
        /// 任务名称
        /// </summary>
        /// <value>The title.</value>
		[Required]
		[MaxLength(512)]
		public String title { get; set; }
        /// <summary>
        /// 任务完成获得金币
        /// </summary>
        /// <value>The coins.</value>
		public int coins { get; set; }
        /// <summary>
        /// 描述
        /// </summary>
        /// <value>The desc.</value>
		[MaxLength(1024)]
		public string desc { get; set; }
    }
}
