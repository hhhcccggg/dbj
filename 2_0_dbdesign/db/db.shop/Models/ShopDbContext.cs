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
        public DbSet<Store> Stores { get; set; }
        public DbSet<LegalSubject> LegalSubjects { get; set; }
        public DbSet<LegalSubjectReview> LegalSubjectReviews { get; set; }
        public DbSet<DeliveryTemplate> DeliveryTemplates { get; set; }
        public DbSet<DeliveryTemplateScope> deliveryTemplateScopes { get; set; }
        public DbSet<Product> Products { get; set; }
        public DbSet<ProductCard> ProductCards { get; set; }
        public DbSet<ProductAttri> ProductAttris { get; set; }
        public DbSet<ProductAttriLink> ProductAttriLinks { get; set; }
        public DbSet<ProductAttriValue> ProductAttriValues { get; set; }
        public DbSet<ProductBrand> ProductBrands { get; set; }
        public DbSet<ProductCart> ProductCarts { get; set; }
        public DbSet<DiscountCoupon> DiscountCoupons { get; set; }
        public DbSet<UserDiscountCoupon> UserDiscountCoupons { get; set; }
        public DbSet<ProductCashCoupon> ProductCashCoupons { get; set; }
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

            var legalSubject = modelBuilder.Entity<LegalSubject>();
            legalSubject.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            legalSubject.Property(c => c.IsDeleted).HasDefaultValue(false);
            legalSubject.Property(cw => cw.isManualData).HasDefaultValue(false);
            legalSubject.Property(cw => cw.status).HasDefaultValue(1);
            legalSubject.Property(cw => cw.reviewed).HasDefaultValue(false);
            legalSubject.Property(cw => cw.status).HasDefaultValue(1);

            var legalSubjectReview = modelBuilder.Entity<LegalSubjectReview>();
            legalSubjectReview.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            legalSubjectReview.Property(c => c.IsDeleted).HasDefaultValue(false);
            legalSubjectReview.Property(cw => cw.isManualData).HasDefaultValue(false);
            legalSubjectReview.HasIndex(cw => cw.identifyId);


            var storeEntity = modelBuilder.Entity<Store>();
            storeEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            storeEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            storeEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            storeEntity.Property(cw => cw.recommendIndex).HasDefaultValue(0);
            storeEntity.Property(cw => cw.longitude).HasDefaultValue(0);
            storeEntity.Property(cw => cw.latitude).HasDefaultValue(0);
            storeEntity.Property(cw => cw.type).HasDefaultValue(0);
            storeEntity.Property(cw => cw.level).HasDefaultValue(0);
            storeEntity.Property(cw => cw.expireTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            storeEntity.Property(cw => cw.grade).HasDefaultValue(0);
            storeEntity.Property(cw => cw.status).HasDefaultValue(0);
            storeEntity.Property(cw => cw.reviewed).HasDefaultValue(false);
            storeEntity.Property(cw => cw.stopService).HasDefaultValue(false);

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
            product.Property(cw => cw.joinMemberDiscount).HasDefaultValue(false);
            product.Property(cw => cw.needDelivery).HasDefaultValue(true);
            product.Property(cw => cw.universalDeliveryPrice).HasDefaultValue(10);
            product.Property(cw => cw.deliverytemplateId).HasDefaultValue(0);
            product.Property(cw => cw.publish).HasDefaultValue(false);
            product.Property(cw => cw.specifyPublishTime).HasDefaultValue(0);
            product.Property(cw => cw.weight).HasDefaultValue(0);
            product.Property(cw => cw.limitPerPerson).HasDefaultValue(0);

            var productCard = modelBuilder.Entity<ProductCard>();
            productCard.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productCard.Property(c => c.IsDeleted).HasDefaultValue(false);
            productCard.Property(cw => cw.isManualData).HasDefaultValue(false);
            productCard.Property(cw => cw.festivalCanUse).HasDefaultValue(true);

            var productCashCoupon = modelBuilder.Entity<ProductCashCoupon>();
            productCashCoupon.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productCashCoupon.Property(c => c.IsDeleted).HasDefaultValue(false);
            productCashCoupon.Property(cw => cw.isManualData).HasDefaultValue(false);
            productCashCoupon.Property(cw => cw.festivalCanUse).HasDefaultValue(true);

            var discountCoupon = modelBuilder.Entity<DiscountCoupon>();
            discountCoupon.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            discountCoupon.Property(c => c.IsDeleted).HasDefaultValue(false);
            discountCoupon.Property(cw => cw.isManualData).HasDefaultValue(false);

            var userDiscountCoupon = modelBuilder.Entity<UserDiscountCoupon>();
            userDiscountCoupon.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            userDiscountCoupon.Property(c => c.IsDeleted).HasDefaultValue(false);
            userDiscountCoupon.Property(cw => cw.isManualData).HasDefaultValue(false);


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
            productOrder.HasIndex(cw => cw.status);

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
            receiveAddress.Property(cw => cw.defaultAddr).HasDefaultValue(false);
            #endregion
        }
    }
}
