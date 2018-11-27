using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.shop.Models.shop
{
    [Table("shop_receiveAddresses")]
    public class ReceiveAddress:db.common.BaseModelWithTime<long>
    {
        /// <summary>
        /// 收货人
        /// </summary>
        /// <value>The name of the receiver.</value>
        [Required]
        [MaxLength(30)]
        public String receiverName { get; set; }
        /// <summary>
        /// 收货人固定电话
        /// </summary>
        /// <value>The receiver phone.</value>
        [MaxLength(30)]
        public String receiverPhone { get; set; }
        /// <summary>
        /// 收货人手机号
        /// </summary>
        /// <value>The receiver mobile.</value>
        [MaxLength(30)]
        public String receiverMobile { get; set; }
        /// <summary>
        /// 省份
        /// </summary>
        /// <value>The state of the reveiver.</value>
        [MaxLength(30)]
        public String reveiverState { get; set; }
        /// <summary>
        /// 城市
        /// </summary>
        /// <value>The receiver city.</value>
        [MaxLength(30)]
        public String receiverCity { get; set; }
        /// <summary>
        /// 区县
        /// </summary>
        /// <value>The receiver country.</value>
        [MaxLength(30)]
        public String receiverCountry { get; set; }
        /// <summary>
        /// 联系地址
        /// </summary>
        /// <value>The receiver address.</value>
        [MaxLength(512)]
        public String receiverAddress { get; set; }
        [MaxLength(20)]
        public String receiverZip { get; set; }
        public long cityId { get; set; }
        [MaxLength(512)]
        public String cityLevel { get; set; }
        [Column("updateTime", TypeName = "timestamp")]
        public DateTime? updateTime { get; set; }
        /// <summary>
        /// 是否为默认地址
        /// </summary>
        /// <value><c>true</c> if is default; otherwise, <c>false</c>.</value>
        public bool isDefault { get; set; }
    }
}
