using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    [Table("core_musics")]
    public class Music : BaseModelWithTime<long>
    {
        [Required]
        [MaxLength(30)]
        public string title { get; set; }
        [MaxLength(30)]
        public string artist { get; set; }
        [MaxLength(512)]
        public string coverUrl { get; set; }
        [MaxLength(512)]
        public string musicUrl { get; set; }
        /// <summary>
        /// 持续时间
        /// </summary>
        /// <value>The duration.</value>
        public float? duration { get; set; }
        public long? categoryId { get; set; }
        public int recommendIndex { get; set; }
        public long? creatorUserId { get; set; }
    }
}
