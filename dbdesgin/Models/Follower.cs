using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    [Table("core_followers")]
    public class Follower : BaseModelWithTime<long>
    {
        public long followerUserId { get; set; }
        public long userId { get; set; }
        public bool isBoth { get; set; }
    }
}
