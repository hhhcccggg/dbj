﻿using System;
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
            #region o2o
            var offstoreExtraService = modelBuilder.Entity<OfflineStoreExtraService>();
            offstoreExtraService.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            offstoreExtraService.Property(c => c.IsDeleted).HasDefaultValue(false);
            offstoreExtraService.Property(cw => cw.isManualData).HasDefaultValue(false);
            offstoreExtraService.Property(cw => cw.status).HasDefaultValue(0);

            var offstoreOpeningHour = modelBuilder.Entity<OfflineStoreOpeningHour>();
            offstoreOpeningHour.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            offstoreOpeningHour.Property(c => c.IsDeleted).HasDefaultValue(false);
            offstoreOpeningHour.Property(cw => cw.isManualData).HasDefaultValue(false);

            var offstoreServiceScope = modelBuilder.Entity<OfflineStoreServiceScope>();
            offstoreServiceScope.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            offstoreServiceScope.Property(c => c.IsDeleted).HasDefaultValue(false);
            offstoreServiceScope.Property(cw => cw.isManualData).HasDefaultValue(false);
            offstoreServiceScope.Property(cw => cw.status).HasDefaultValue(0);

            var offstoreStaff = modelBuilder.Entity<OfflineStoreStaff>();
            offstoreStaff.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            offstoreStaff.Property(c => c.IsDeleted).HasDefaultValue(false);
            offstoreStaff.Property(cw => cw.isManualData).HasDefaultValue(false);
            offstoreStaff.Property(cw => cw.isSuperStar).HasDefaultValue(false);

            var businessSeller = modelBuilder.Entity<BusinessSeller>();
            businessSeller.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            businessSeller.Property(c => c.IsDeleted).HasDefaultValue(false);
            businessSeller.Property(cw => cw.isManualData).HasDefaultValue(false);
            businessSeller.Property(cw => cw.recommendIndex).HasDefaultValue(0);
            businessSeller.Property(cw => cw.longitude).HasDefaultValue(0);
            businessSeller.Property(cw => cw.latitude).HasDefaultValue(0);
            businessSeller.Property(cw => cw.type).HasDefaultValue(0);
            businessSeller.Property(cw => cw.level).HasDefaultValue(0);
            businessSeller.Property(cw => cw.expireTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            businessSeller.Property(cw => cw.grade).HasDefaultValue(0);
            businessSeller.Property(cw => cw.status).HasDefaultValue(0);
            businessSeller.Property(cw => cw.isReviewed).HasDefaultValue(false);
            businessSeller.Property(cw => cw.isStopService).HasDefaultValue(false);

            var businessSellerReview = modelBuilder.Entity<BusinessSellerReview>();
            businessSellerReview.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            businessSellerReview.Property(c => c.IsDeleted).HasDefaultValue(false);
            businessSellerReview.Property(cw => cw.isManualData).HasDefaultValue(false);
            businessSellerReview.Property(cw => cw.status).HasDefaultValue(0);

            var deliveryTemplate = modelBuilder.Entity<DeliveryTemplate>();
            deliveryTemplate.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            deliveryTemplate.Property(c => c.IsDeleted).HasDefaultValue(false);
            deliveryTemplate.Property(cw => cw.isManualData).HasDefaultValue(false);
            deliveryTemplate.Property(cw => cw.billType).HasDefaultValue(0);

            var deliveryTemplateScope = modelBuilder.Entity<DeliveryTemplateScope>();
            deliveryTemplateScope.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            deliveryTemplateScope.Property(c => c.IsDeleted).HasDefaultValue(false);
            deliveryTemplateScope.Property(cw => cw.isManualData).HasDefaultValue(false);
            deliveryTemplateScope.Property(cw => cw.item).HasDefaultValue(1);
            deliveryTemplateScope.Property(cw => cw.itemPrice).HasDefaultValue(1);
            deliveryTemplateScope.Property(cw => cw.overItem).HasDefaultValue(1);
            deliveryTemplateScope.Property(cw => cw.overItemPrice).HasDefaultValue(1);

            var product = modelBuilder.Entity<Product>();
            product.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            product.Property(c => c.IsDeleted).HasDefaultValue(false);
            product.Property(cw => cw.isManualData).HasDefaultValue(false);
            product.Property(cw => cw.productType).HasDefaultValue(0);
            product.Property(cw => cw.commentCount).HasDefaultValue(0);
            product.Property(cw => cw.grade).HasDefaultValue(0);
            product.Property(cw => cw.sales).HasDefaultValue(0);
            product.Property(cw => cw.inventory).HasDefaultValue(0);
            product.Property(cw => cw.priceUp).HasDefaultValue(0);
            product.Property(cw => cw.priceDown).HasDefaultValue(0);
            product.Property(cw => cw.productGroupId).HasDefaultValue(0);
            product.Property(cw => cw.isJoinMemberDiscount).HasDefaultValue(false);
            product.Property(cw => cw.isNeedDelivery).HasDefaultValue(true);
            product.Property(cw => cw.universalDeliveryPrice).HasDefaultValue(10);
            product.Property(cw => cw.deliverytemplateId).HasDefaultValue(0);
            product.Property(cw => cw.isPublish).HasDefaultValue(false);
            product.Property(cw => cw.specifyPublishTime).HasDefaultValue(0);
            product.Property(cw => cw.weight).HasDefaultValue(0);

            var productAttri = modelBuilder.Entity<ProductAttri>();
            productAttri.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productAttri.Property(c => c.IsDeleted).HasDefaultValue(false);
            productAttri.Property(cw => cw.isManualData).HasDefaultValue(false);

            var productAttriLink = modelBuilder.Entity<ProductAttriLink>();
            productAttriLink.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productAttriLink.Property(c => c.IsDeleted).HasDefaultValue(false);
            productAttriLink.Property(cw => cw.isManualData).HasDefaultValue(false);

            var productAttriValue = modelBuilder.Entity<ProductAttriValue>();
            productAttriValue.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productAttriValue.Property(c => c.IsDeleted).HasDefaultValue(false);
            productAttriValue.Property(cw => cw.isManualData).HasDefaultValue(false);

            var productBrand = modelBuilder.Entity<ProductBrand>();
            productBrand.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productBrand.Property(c => c.IsDeleted).HasDefaultValue(false);
            productBrand.Property(cw => cw.isManualData).HasDefaultValue(false);
            productBrand.Property(cw => cw.orderIndex).HasDefaultValue(0);

            var productCart = modelBuilder.Entity<ProductCart>();
            productCart.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productCart.Property(c => c.IsDeleted).HasDefaultValue(false);
            productCart.Property(cw => cw.isManualData).HasDefaultValue(false);

            var productOrder = modelBuilder.Entity<ProductOrder>();
            productOrder.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productOrder.Property(c => c.IsDeleted).HasDefaultValue(false);
            productOrder.Property(cw => cw.isManualData).HasDefaultValue(false);

            var productOrderItem = modelBuilder.Entity<ProductOrderItem>();
            productOrderItem.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productOrderItem.Property(c => c.IsDeleted).HasDefaultValue(false);
            productOrderItem.Property(cw => cw.isManualData).HasDefaultValue(false);

            var productsku = modelBuilder.Entity<ProductSKU>();
            productsku.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productsku.Property(c => c.IsDeleted).HasDefaultValue(false);
            productsku.Property(cw => cw.isManualData).HasDefaultValue(false);
            productsku.Property(cw => cw.sales).HasDefaultValue(0);

            var receiveAddress = modelBuilder.Entity<ReceiveAddress>();
            receiveAddress.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            receiveAddress.Property(c => c.IsDeleted).HasDefaultValue(false);
            receiveAddress.Property(cw => cw.isManualData).HasDefaultValue(false);
            receiveAddress.Property(cw => cw.cityId).HasDefaultValue(0);
            receiveAddress.Property(cw => cw.isDefault).HasDefaultValue(false);
            #endregion
        }
    }
}
