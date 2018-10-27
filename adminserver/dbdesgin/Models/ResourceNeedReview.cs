using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    public enum ResourceNeedReviewDataType {
        USER_AVATAR = 0,
        PET_AVATAR = 1,
        VIDEO_RES = 2
    }
    [Table("core_resourceNeedReviews")]
    public class ResourceNeedReview : BaseModelWithTime<long>
    {
        /// <summary>
        /// 资源内容,当前是七牛的key值
        /// </summary>
        /// <value>The content of the res.</value>
        [MaxLength(512)]
        public string resContent { get; set; }
        /// <summary>
        /// 0:七牛图片1:七牛视频
        /// </summary>
        /// <value>The type of the res.</value>
        public int resType { get; set; }
        /// <summary>
        /// 关联数据ID
        /// </summary>
        /// <value>The data identifier.</value>
        public long dataId { get; set; }
        /// <summary>
        /// 0:用户头像1:宠物头像2:短视频
        /// </summary>
        /// <value>The type of the data.</value>
        public int dataType { get; set; }
        /// <summary>
        /// 审核结果的类型
        /// pass:通过block:拒绝revie:需要人工介入
        /// </summary>
        /// <value>The type of the review result.</value>
        [MaxLength(20)]
        public string reviewResultType { get; set; }
        /// <summary>
        /// 没有通过的原因
        /// </summary>
        /// <value>The content of the review result.</value>
        [MaxLength(1024)]
        public string reviewResultContent { get; set; }
    }
}
