using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models
{
    /// <summary>
    /// 城市行政列表
    /// </summary>
    [Table("core_cities")]
    public class City:db.common.BaseModel<long>
    {
        [Required]
        [MaxLength(128)]
        public String name { get; set; }
        [MaxLength(128)]
        public string zipcode { get; set; }
        [MaxLength(128)]
        public string citycode { get; set; }
        /// <summary>
        /// 经度
        /// </summary>
        /// <value>The longitude.</value>
        public float longitude { get; set; }
        /// <summary>
        /// 维度
        /// </summary>
        /// <value>The latitude.</value>
        public float latitude { get; set; }
        /// <summary>
        /// province:省、直辖市、自治州
        /// city:市
        /// district:行政区
        /// street: 街道、镇、乡
        /// </summary>
        /// <value>The level.</value>
        [Required]
        [MaxLength(128)]
        public string level { get; set; }
        public long parentId { get; set; }
    }
}
