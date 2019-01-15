using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models
{
    /// <summary>
    /// 用户任务情况
    /// </summary>
	[Table("core_userTasks")]
	public class UserTask:db.common.BaseModelWithTime<long>
    {
        [Required]
		[MaxLength(128)]
		public string taskId { get; set; }
		public long userId { get; set; }
        /// <summary>
        /// 获得的金币
        /// </summary>
        /// <value>The coins.</value>
		public int coins { get; set; }
        /// <summary>
        /// 状态
		/// NONE
		/// DOING:进行中
		/// DONE:完成
        /// </summary>
        /// <value>The state.</value>
		[Required]
		[MaxLength(128)]
		public string state { get; set; }
        /// <summary>
        /// 描述
        /// </summary>
        /// <value>The desc.</value>
		[MaxLength(1024)]
		public string desc { get; set; }
    }
}
