using System;
using System.ComponentModel.DataAnnotations.Schema;
using db.common;

namespace dbdesgin.Models
{
    [Table("core_messageDispatchs")]
    public class MessageDispatch:BaseModelWithTime<long>
    {
        public long refMessageId { get; set; }
        public long receivedUserId { get; set; }
        /// <summary>
        /// 0:未读1：已读2:删除
        /// </summary>
        public int status { get; set; }
    }
}
