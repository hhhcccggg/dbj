using System;
using Microsoft.EntityFrameworkCore;
using db.shop.Models.o2o;
using db.shop.Models.shop;
using System.Linq;
using System.Diagnostics;

namespace db.shop.Models
{
    public class ShopDbContext:DbContext
    {
        public ShopDbContext(DbContextOptions<ShopDbContext> options)
            :base(options)
        {
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }
    }
}
