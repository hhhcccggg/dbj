using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    [Table("core_messageBroadcasts")]
    public class MessageBroadcast
    {
        [Key]
        public long receivedUserId { get; set; }
        public long lastMessageId { get; set; }
    }
}
