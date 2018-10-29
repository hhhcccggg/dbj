using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace dbdesgin.Models
{
    [Table("core_pets")]
    public class Pet:BaseModelWithTime<long>
    {
        [MaxLength(512)]
        [Required]
        public String avatar { get; set; }
        [Required]
        [MaxLength(20)]
        public String nickName { get; set; }
        [Required]
        [Column(TypeName = "timestamp")]
        public DateTime birthday { get; set; }
        public int sex { get; set; }
        public long categoryId { get; set; }
        [Required]
        public long userId { get; set; }
    }
}
