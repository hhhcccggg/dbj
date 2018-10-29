using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    [Table("core_livings")]
    public class Living : BaseModelWithTime<long>
    {
        [MaxLength(50)]
        [Required]
        public string title { get; set; }
        [MaxLength(512)]
        public string coverUrl { get; set; }
        public string linkPets { get; set; }
        public float? longitude { get; set; }
        public float? latitude { get; set; }
        [MaxLength(512)]
        public string address { get; set; }
        /// <summary>
        /// 0:正常1:审核中2:拒绝3:下架
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
        [MaxLength(512)]
        public string rejectMsg { get; set; }
        /// <summary>
        /// 推荐指数
        /// </summary>
        /// <value>The index of the recommend.</value>
        public int recommendIndex { get; set; }
        public long playCount { get; set; }
        public long commentCount { get; set; }
        public long heartCount { get; set; }
        public long shareCount { get; set; }
        /// <summary>
        /// 同时在线人数
        /// </summary>
        /// <value>The online people count.</value>
        public long onlinePeopleCount { get; set; }
        [Required]
        public long userId { get; set; }
        [MaxLength(512)]
        public string pushUrl { get; set; }
        [MaxLength(512)]
        public string pullUrl { get; set; }
        [MaxLength(512)]
        public string hlsUrl { get; set; }
        /// <summary>
        /// 直播时长
        /// </summary>
        /// <value>The liveing total time.</value>
        public long liveingTotalTime { get; set; }
        /// <summary>
        /// 获得的金币数
        /// </summary>
        /// <value>The get coins.</value>
        public long getCoins { get; set; }
        /// <summary>
        /// 获取的粉丝数
        /// </summary>
        /// <value>The get friends.</value>
        public long getFriends { get; set; }
        /// <summary>
        /// 是否正在直播
        /// </summary>
        /// <value><c>true</c> if is living; otherwise, <c>false</c>.</value>
        public bool isLiving { get; set; }
        /// <summary>
        /// 关联的产品
        /// </summary>
        /// <value>The link product count.</value>
        public int linkProductCount { get; set; }
        /// <summary>
        /// 投诉举报次数
        /// </summary>
        /// <value>The complain count.</value>
        public int complainCount { get; set; }
        /// <summary>
        /// 聊天室编号
        /// </summary>
        /// <value>The chat room identifier.</value>
        [MaxLength(512)]
        public string chatRoomId { get; set; }

    }
}
