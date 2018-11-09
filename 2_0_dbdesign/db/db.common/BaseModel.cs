using System;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.common
{
    public class BaseModel<TKey>
    {
        [Key]
        [Column("id")]
        [DatabaseGenerated(DatabaseGeneratedOption.None)]
        [MaxLength(128)]
        public TKey Id { get; set; }
    }

    public class BaseModelWithTime<TKey> : BaseModel<TKey>
    {
        [Column("createTime", TypeName = "timestamp")]
        public DateTime CreateTime { get; set; }
        [Column("isDeleted")]
        public Boolean IsDeleted { get; set; }
        [Column("deleteTime", TypeName = "timestamp")]
        public DateTime? DeleteTime { get; set; }
    }
}
