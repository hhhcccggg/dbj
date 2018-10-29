using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    /// <summary>
    /// 关联的商品
    /// </summary>
    [Table("core_resourceRefGoods")]
    public class ResourceRefGoods:BaseModelWithTime<long>
    {
        public long resourceId { get; set; }
        /// <summary>
        /// 0短视频1：直播
        /// </summary>
        public int resourceType { get; set; }
        /// <summary>
        /// 商品的ID，多个商品使用英文状态下逗号(,)隔开
        /// </summary>
        [MaxLength(1024)]
        public String goods { get; set; }
    }
}
