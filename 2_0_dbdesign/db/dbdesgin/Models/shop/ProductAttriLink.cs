using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace db.video.Models.shop
{
    /// <summary>
    /// 产品属性关系表
    /// </summary>
    [Table("shop_productAttriLinks")]
    public class ProductAttriLink:db.common.BaseModelWithTime<long>
    {

    }
}
