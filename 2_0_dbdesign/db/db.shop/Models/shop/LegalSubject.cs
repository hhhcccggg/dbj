using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    /// <summary>
    /// 商家
    /// </summary>
    [Table("core_legalSubjects")]
    public class LegalSubject : db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 名称
        /// </summary>
        /// <value>The name.</value>
        [Required]
        [MaxLength(128)]
        public String name { get; set; }
        [MaxLength(128)]
        public String subName { get; set; }
        [MaxLength(128)]
        public String marketName { get; set; }
        [MaxLength(2048)]
        public String description { get; set; }
        /// <summary>
        /// 企业编号
        /// </summary>
        /// <value>The corp identifier.</value>
        [MaxLength(512)]
        public String corpId { get; set; }
        /// <summary>
        /// 法人代表姓名
        /// </summary>
        /// <value>The name of the leagal representative.</value>
        [Required]
        [MaxLength(128)]
        public String leagalRepresentativeName { get; set; }
        /// <summary>
        /// 法人代表身份证号码
        /// </summary>
        /// <value>The leagal representative identifier.</value>
        [Required]
        [MaxLength(512)]
        public String leagalRepresentativeID { get; set; }
        /// <summary>
        /// logo地址
        /// </summary>
        /// <value>The logo URL.</value>
        [MaxLength(512)]
        public String logoUrl { get; set; }
        /// <summary>
        /// 联系地址
        /// </summary>
        /// <value>The address.</value>
        [MaxLength(1024)]
        public String contactAddress { get; set; }
        /// <summary>
        /// 企业注册地址
        /// </summary>
        /// <value>The reg address.</value>
        public String regAddress { get; set; }
        /// <summary>
        /// 企业注册地城市编号
        /// </summary>
        /// <value>The city identifier.</value>
        public int cityId { get; set; }
        [MaxLength(512)]
        public String cityLevel { get; set; }
        [Required]
        [MaxLength(50)]
        public String contactName { get; set; }
        [Required]
        [MaxLength(50)]
        public String contactPhone { get; set; }
        [MaxLength(50)]
        public String qq { get; set; }
        [MaxLength(50)]
        public String wechat { get; set; }
        /// <summary>
        /// 0：正常1：关闭
        /// </summary>
        /// <value>The status.</value>
        public int status { get; set; }
        /// <summary>
        /// 是否已审核通过
        /// </summary>
        /// <value><c>true</c> if is reviewed; otherwise, <c>false</c>.</value>
        public bool reviewed { get; set; }
        /// <summary>
        /// 审核说明
        /// </summary>
        /// <value>The review message.</value>
        [MaxLength(2048)]
        public String reviewMsg { get; set; }
        /// <summary>
        /// 类型
        /// P:个人；B:企业
        /// </summary>
        /// <value>The type.</value>
        [Required]
        [MaxLength(50)]
        public String type { get; set; }
    }
}
