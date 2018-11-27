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

        #region o2o
        public DbSet<OfflineStoreExtraService> OfflineStoreExtraServices { get; set; }
        public DbSet<OfflineStoreOpeningHour> OfflineStoreOpeningHours { get; set; }
        public DbSet<OfflineStoreStaff> OfflineStoreStaffs { get; set; }
        public DbSet<OfflineStoreServiceScope> OfflineStoreServiceScopes { get; set; }
        #endregion

        #region shop
        public DbSet<BusinessSeller> BusinessSellers { get; set; }
        public DbSet<BusinessSellerReview> BusinessSellerReviews { get; set; }
        public DbSet<DeliveryTemplate> DeliveryTemplates { get; set; }
        public DbSet<DeliveryTemplateScope> deliveryTemplateScopes { get; set; }
        public DbSet<Product> Products { get; set; }
        public DbSet<ProductAttri> ProductAttris { get; set; }
        public DbSet<ProductAttriLink> ProductAttriLinks { get; set; }
        public DbSet<ProductAttriValue> ProductAttriValues { get; set; }
        public DbSet<ProductBrand> ProductBrands { get; set; }
        public DbSet<ProductCart> ProductCarts { get; set; }
        public DbSet<ProductOrder> ProductOrders { get; set; }
        public DbSet<ProductOrderItem> ProductOrderItems { get; set; }
        public DbSet<ProductSKU> GetProductSKUs { get; set; }
        public DbSet<ReceiveAddress> ReceiveAddresses { get; set; }
        #endregion

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }
    }
}
