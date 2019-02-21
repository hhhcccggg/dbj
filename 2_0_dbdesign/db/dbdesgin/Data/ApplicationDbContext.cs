using System;
using System.Collections.Generic;
using System.Text;
using dbdesgin.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;
using db.video.Models;
using db.shop.Models.shop;
using db.shop.Models.o2o;

namespace dbdesgin.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        #region o2o
        public DbSet<OfflineStoreExtraService> OfflineStoreExtraServices { get; set; }
        public DbSet<OfflineStoreOpeningHour> OfflineStoreOpeningHours { get; set; }
        public DbSet<OfflineStoreStaff> OfflineStoreStaffs { get; set; }
        public DbSet<OfflineStoreServiceScope> OfflineStoreServiceScopes { get; set; }
        #endregion

        #region shop
        public DbSet<Favorite> Favorites { get; set; }
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
        public DbSet<LegalSubjectAsset> LegalSubjectAssets { get; set; }
        public DbSet<LegalSubjectAssetDetail> LegalSubjectAssetDetails { get; set; }
        #endregion


        public DbSet<City> Cities { get; set; }
        public DbSet<AdBanner> AdBanners { get; set; }

        public DbSet<User> Users { get; set; }
        public DbSet<Tenant> Tenants { get; set; }
        public DbSet<UserDeviceToken> UserDeviceTokens { get; set; }
        public DbSet<UserThirdAccountBind> UserThirdAccountBinds { get; set; }
        public DbSet<Pet> Pets { get; set; }
        public DbSet<Category> Categories { get; set; }
        public DbSet<Video> Videos { get; set; }
        public DbSet<Living> Livings { get; set; }
        public DbSet<Tag> Tags { get; set; }
        public DbSet<Music> Musics { get; set; }
        public DbSet<Comment> Comments { get; set; }
        public DbSet<Heart> Hearts { get; set; }
        public DbSet<Follower> Followers { get; set; }

        public DbSet<UserAsset> UserAssets { get; set; }
        public DbSet<UserCoinType> userCoinTypes { get; set; }
        public DbSet<UserCoinDetail> UserCoinDetails { get; set; }


		public DbSet<UserInvitation> UserInvitations { get; set; }
		public DbSet<Task> Tasks { get; set; }
		public DbSet<UserTask> UserTasks { get; set; }

        public DbSet<EnCashPayAccount> EnCashPayAccounts { get; set; }
        public DbSet<EnCashMentDetail> EnCashMentDetails { get; set; }

        public DbSet<ResourceRefGoods> ResourceRefGoods { get; set; }

        public DbSet<Role> Roles { get; set; }
        public DbSet<Permission> Permissions { get; set; }
        public DbSet<UserRole> UserRoles { get; set; }
        public DbSet<RolePermission> RolePermissions { get; set; }

        public DbSet<Complain> Complains { get; set; }
        public DbSet<ComplainReason> ComplainReasons { get; set; }

        public DbSet<Message> Messages { get; set; }
        public DbSet<MessageDispatch> MessageDispatches { get; set; }
        public DbSet<MessageBroadcast> MessageBroadcasts { get; set; }

        public DbSet<ResourceNeedReview> ResourceNeedReviews { get; set; }

        public DbSet<DailyIncreaseAnalysis> DailyIncreaseAnalyses { get; set; }

        public DbSet<AppVersion> AppVersions { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);

            var resNeedReview = modelBuilder.Entity<ResourceNeedReview>();
            resNeedReview.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            resNeedReview.Property(c => c.IsDeleted).HasDefaultValue(false);
            resNeedReview.Property(cw => cw.isManualData).HasDefaultValue(false);
            resNeedReview.Property(c => c.resType).HasDefaultValue(0);
            resNeedReview.Property(c => c.dataId).HasDefaultValue(0);
            resNeedReview.Property(c => c.dataType).HasDefaultValue(0);

			var userInvitation = modelBuilder.Entity<UserInvitation>();
			userInvitation.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
			userInvitation.Property(c => c.IsDeleted).HasDefaultValue(false);
			userInvitation.Property(cw => cw.isManualData).HasDefaultValue(false);

			var taskEntity = modelBuilder.Entity<Task>();
			taskEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
			taskEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
			taskEntity.Property(cw => cw.isManualData).HasDefaultValue(false);

			var userTaskEntity = modelBuilder.Entity<UserTask>();
			userTaskEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
			userTaskEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
			userTaskEntity.Property(cw => cw.isManualData).HasDefaultValue(false);

            //Category
            var categoryEntity = modelBuilder.Entity<Category>();
            categoryEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            categoryEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            categoryEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            categoryEntity.Property(c => c.parentId).HasDefaultValue(0);
            categoryEntity.Property(c => c.type).HasDefaultValue(0);
            categoryEntity.HasIndex(c => c.userId);
            //AppVersion
            var appVersionEntity = modelBuilder.Entity<AppVersion>();
            appVersionEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            appVersionEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            appVersionEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            appVersionEntity.Property(c => c.upgradeType).HasDefaultValue(0);
            // UserDeviceToken
            var userDeviceTokenEntity = modelBuilder.Entity<UserDeviceToken>();
            userDeviceTokenEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            userDeviceTokenEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            userDeviceTokenEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            userDeviceTokenEntity.Property(c => c.userId).HasDefaultValue(0);

            //UserAsset
            var userAssetEntity = modelBuilder.Entity<UserAsset>();
            userAssetEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            userAssetEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            userAssetEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
			userAssetEntity.Property(cw => cw.totalCoins).HasDefaultValue(0);
            userAssetEntity.HasIndex(c => c.userId);
            //UserCoinType
            var userCoinTypeEntity = modelBuilder.Entity<UserCoinType>();
            userCoinTypeEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            userCoinTypeEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            userCoinTypeEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            userCoinTypeEntity.HasIndex(c => c.userId);
            userCoinTypeEntity.Property(c => c.lockedCoins).HasDefaultValue(0);
            // userCoinDetail
            var userCoinDetailEntity = modelBuilder.Entity<UserCoinDetail>();
            userCoinDetailEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            userCoinDetailEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            userCoinDetailEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            userCoinDetailEntity.HasIndex(c => c.userId);
            userCoinDetailEntity.HasIndex(c => c.tradeNo);
            // enCashMentAccountEntity
            var enCashMentAccountEntity = modelBuilder.Entity<EnCashPayAccount>();
            enCashMentAccountEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            enCashMentAccountEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            enCashMentAccountEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            enCashMentAccountEntity.Property(cw => cw.isLocked).HasDefaultValue(false);
            enCashMentAccountEntity.HasIndex(cw => cw.userId);
            // enCashMentDetailEntity
            var enCashMentDetailEntity = modelBuilder.Entity<EnCashMentDetail>();
            enCashMentDetailEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            enCashMentDetailEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            enCashMentDetailEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            enCashMentDetailEntity.HasIndex(cw => cw.userId);
            enCashMentDetailEntity.Property(cw => cw.isAllowedEnCash).HasDefaultValue(false);
            enCashMentDetailEntity.HasIndex(cw => cw.tradeNo);

            //Tenant
            var tenantEntity = modelBuilder.Entity<Tenant>();
            tenantEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            tenantEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            tenantEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            tenantEntity.HasIndex(cw => cw.identifyName);
            tenantEntity.Property(cw => cw.legalSubjectId).HasDefaultValue(0);

            //abBanner
            var adBannerEntity = modelBuilder.Entity<AdBanner>();
            adBannerEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            adBannerEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            adBannerEntity.Property(cw => cw.isManualData).HasDefaultValue(false);

            //User
            var userEntity = modelBuilder.Entity<User>();
            userEntity.HasIndex(g => g.UserName)
                        .IsUnique();
            userEntity.HasIndex(g => g.Email).IsUnique();
            userEntity.HasIndex(g => g.Phone).IsUnique();
            userEntity.Property(c => c.IsLocked).HasDefaultValue(false);
            userEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            userEntity.Property(c => c.IsEmailVerification).HasDefaultValue(false);
            userEntity.Property(c => c.IsPhoneVerification).HasDefaultValue(false);
            userEntity.Property(c => c.isLivingOpen).HasDefaultValue(false);
            userEntity.Property(c => c.isLiving).HasDefaultValue(false);
            userEntity.Property(c => c.totalHearts).HasDefaultValue(0);
            userEntity.Property(c => c.totalFans).HasDefaultValue(0);
            userEntity.Property(c => c.totalMyFocuses).HasDefaultValue(0);
            userEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            userEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            userEntity.Property(c => c.livingId).HasDefaultValue(0);
            userEntity.Property(c => c.isSuper).HasDefaultValue(false);
            userEntity.Property(c => c.isReviewed).HasDefaultValue(false);
            userEntity.Property(c => c.loginType).HasDefaultValue(1);
            userEntity.Property(c => c.complainCount).HasDefaultValue(0);
            userEntity.Property(cw => cw.isManager).HasDefaultValue(false);
            userEntity.Property(cw => cw.sex).HasDefaultValue(0);
            userEntity.Property(cw => cw.occupationId).HasDefaultValue(0);
            userEntity.Property(cw => cw.loveStatusId).HasDefaultValue(0);
            userEntity.Property(cw => cw.isLivingWatch).HasDefaultValue(false);
            userEntity.Property(cw => cw.type).HasDefaultValue("NORMAL");
            //UserThirdAccountBind
            var userActBindEntity = modelBuilder.Entity<UserThirdAccountBind>();
            userActBindEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            userActBindEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            userActBindEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            userActBindEntity.Property(c => c.nickName).HasDefaultValue("");

            //Living
            var livingEntity = modelBuilder.Entity<Living>();
            livingEntity.Property(c => c.status).HasDefaultValue(0);
            livingEntity.Property(c => c.recommendIndex).HasDefaultValue(0);
            livingEntity.Property(c => c.playCount).HasDefaultValue(0);
            livingEntity.Property(c => c.commentCount).HasDefaultValue(0);
            livingEntity.Property(c => c.heartCount).HasDefaultValue(0);
            livingEntity.Property(c => c.shareCount).HasDefaultValue(0);
            livingEntity.Property(c => c.onlinePeopleCount).HasDefaultValue(0);
            livingEntity.Property(c => c.getCoins).HasDefaultValue(0);
            livingEntity.Property(c => c.getFriends).HasDefaultValue(0);
            livingEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            livingEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            livingEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            livingEntity.Property(c => c.liveingTotalTime).HasDefaultValue(0);
            livingEntity.Property(c => c.isLiving).HasDefaultValue(false);
            livingEntity.Property(c => c.linkProductCount).HasDefaultValue(0);
            livingEntity.Property(c => c.complainCount).HasDefaultValue(0);
            //Music
            var musicEntity = modelBuilder.Entity<Music>();
            musicEntity.Property(c => c.recommendIndex).HasDefaultValue(0);
            musicEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            musicEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            musicEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            //Tag
            var tagEntity = modelBuilder.Entity<Tag>();
            tagEntity.Property(c => c.recommendIndex).HasDefaultValue(0);
            tagEntity.Property(c => c.resNumber).HasDefaultValue(0);
            tagEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            tagEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            tagEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            tagEntity.Property(cw => cw.isHot).HasDefaultValue(false);
            //Video
            var videoEntity = modelBuilder.Entity<Video>();
            videoEntity.Property(c => c.status).HasDefaultValue(1);
            videoEntity.Property(c => c.recommendIndex).HasDefaultValue(0);
            videoEntity.Property(c => c.playCount).HasDefaultValue(0);
            videoEntity.Property(c => c.commentCount).HasDefaultValue(0);
            videoEntity.Property(c => c.heartCount).HasDefaultValue(0);
            videoEntity.Property(c => c.shareCount).HasDefaultValue(0);
            videoEntity.Property(c => c.isHiddenLocation).HasDefaultValue(false);
            videoEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            videoEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            videoEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            videoEntity.Property(c => c.linkProductCount).HasDefaultValue(0);
            videoEntity.Property(c => c.coverImageWidth).HasDefaultValue(0);
            videoEntity.Property(c => c.coverImageHeight).HasDefaultValue(0);
            videoEntity.Property(c => c.complainCount).HasDefaultValue(0);
            videoEntity.Property(c => c.firstFrameWidth).HasDefaultValue(0);
            videoEntity.Property(c => c.firstFrameHeight).HasDefaultValue(0);
            videoEntity.Property(c=>c.isManualRecommend).HasDefaultValue(false);
            videoEntity.HasIndex(c => c.userId);
            videoEntity.Property(cw => cw.tipCount).HasDefaultValue(0);
            videoEntity.Property(cw => cw.type).HasDefaultValue("USER");
            //videoTipDetail
            var videoTipDetailEntity = modelBuilder.Entity<VideoTipDetail>();
            videoTipDetailEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            videoTipDetailEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            videoTipDetailEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            videoTipDetailEntity.HasIndex(cw => cw.userId);
            videoTipDetailEntity.HasIndex(cw => cw.videoId);
            //buyCoinConfig
            var buyCoinConfigEntity = modelBuilder.Entity<BuyCoinConfig>();
            buyCoinConfigEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            buyCoinConfigEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            buyCoinConfigEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            buyCoinConfigEntity.Property(cw => cw.orderIndex).HasDefaultValue(0);
            buyCoinConfigEntity.Property(cw => cw.type).HasDefaultValue("ANDROID");
            //Pet
            var petEntity = modelBuilder.Entity<Pet>();
            petEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            petEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            petEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            petEntity.Property(c => c.birthday).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            petEntity.Property(c => c.sex).HasDefaultValue(0);
            petEntity.Property(c => c.categoryId).HasDefaultValue(0);
            petEntity.HasIndex(c => c.userId);

            //Comment
            var commentEntity = modelBuilder.Entity<Comment>();
            commentEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            commentEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            commentEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            commentEntity.Property(c => c.heartCount).HasDefaultValue(0);
            commentEntity.Property(c => c.resourceTypeId).HasDefaultValue(0);
            commentEntity.Property(c => c.refCommentId).HasDefaultValue(0);
            commentEntity.Property(c => c.isOwner).HasDefaultValue(false);
            commentEntity.Property(c => c.rate).HasDefaultValue(0.0f);
            //CommentExtraData
            var commentExtraDataEntity = modelBuilder.Entity<CommentExtraData>();
            commentExtraDataEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            commentExtraDataEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            commentExtraDataEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            commentExtraDataEntity.Property(c => c.type).HasDefaultValue("VIDEO");
            commentExtraDataEntity.Property(c => c.dataId).HasDefaultValue(0);

            //Heart
            var heartEntity = modelBuilder.Entity<Heart>();
            heartEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            heartEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            heartEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            heartEntity.Property(c => c.resourceTypeId).HasDefaultValue(0);

            //Follower
            var followerEntity = modelBuilder.Entity<Follower>();
            followerEntity.HasIndex(cw => cw.userId);
            followerEntity.HasIndex(cw => cw.followerUserId);
            followerEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            followerEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            followerEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            followerEntity.Property(c => c.isBoth).HasDefaultValue(false);

            //Complain
            var complainEntity = modelBuilder.Entity<Complain>();
            complainEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            complainEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            complainEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            complainEntity.Property(c => c.fromTypeId).HasDefaultValue(0);

            //ComplainReason
            var complainReason = modelBuilder.Entity<ComplainReason>();
            complainReason.Property(c => c.isOpen).HasDefaultValue(true);

            //Message
            var messageEntity = modelBuilder.Entity<Message>();
            messageEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            messageEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            messageEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            messageEntity.Property(cw=>cw.messageType).HasDefaultValue(0);
            //MessageDispatch
            var messageDispatchEntity = modelBuilder.Entity<MessageDispatch>();
            messageDispatchEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            messageDispatchEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            messageDispatchEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            messageDispatchEntity.Property(c => c.status).HasDefaultValue(0);
            //ResourceRefGoods
            var resourceRefGoodsEntity = modelBuilder.Entity<ResourceRefGoods>();
            resourceRefGoodsEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            resourceRefGoodsEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            resourceRefGoodsEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            resourceRefGoodsEntity.Property(c => c.resourceType).HasDefaultValue(0);
            //DailyIncreaseAnalysis
            var dailyIncreaseAnalysis = modelBuilder.Entity<DailyIncreaseAnalysis>();
            dailyIncreaseAnalysis.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            dailyIncreaseAnalysis.Property(c => c.IsDeleted).HasDefaultValue(false);
            dailyIncreaseAnalysis.Property(cw => cw.isManualData).HasDefaultValue(false);
            dailyIncreaseAnalysis.Property(c => c.newUsers).HasDefaultValue(0);
            dailyIncreaseAnalysis.Property(c => c.newVideos).HasDefaultValue(0);
            dailyIncreaseAnalysis.Property(c => c.newOrders).HasDefaultValue(0);
            dailyIncreaseAnalysis.Property(c => c.perDau).HasDefaultValue(0);
            dailyIncreaseAnalysis.Property(c => c.perMau).HasDefaultValue(0);
            dailyIncreaseAnalysis.Property(c => c.type).HasDefaultValue("FAKE");

            #region 权限
            // 角色
            var roleEntity = modelBuilder.Entity<Role>();
            roleEntity.Property(cw => cw.description).HasDefaultValue("");
            // 权限
            var permissionEntity = modelBuilder.Entity<Permission>();
            permissionEntity.Property(cw => cw.description).HasDefaultValue("");
            var userRoleEntity = modelBuilder.Entity<UserRole>();
            userRoleEntity.HasIndex(c => c.userId);

            #endregion


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
            product.Property(cw => cw.supportCoin).HasDefaultValue(false);

            var productCard = modelBuilder.Entity<ProductCard>();
            productCard.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productCard.Property(c => c.IsDeleted).HasDefaultValue(false);
            productCard.Property(cw => cw.isManualData).HasDefaultValue(false);
            productCard.Property(cw => cw.festivalCanUse).HasDefaultValue(true);
            productCard.Property(cw => cw.stackUse).HasDefaultValue(false);

            var productCashCoupon = modelBuilder.Entity<ProductCashCoupon>();
            productCashCoupon.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            productCashCoupon.Property(c => c.IsDeleted).HasDefaultValue(false);
            productCashCoupon.Property(cw => cw.isManualData).HasDefaultValue(false);
            productCashCoupon.Property(cw => cw.festivalCanUse).HasDefaultValue(true);
            productCashCoupon.Property(cw => cw.stackUse).HasDefaultValue(false);

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
            productOrder.Property(cw => cw.useCoin).HasDefaultValue(0);

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


            var legalSubjectAsset = modelBuilder.Entity<LegalSubjectAsset>();
            legalSubjectAsset.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            legalSubjectAsset.Property(c => c.IsDeleted).HasDefaultValue(false);
            legalSubjectAsset.Property(cw => cw.isManualData).HasDefaultValue(false);

            var legalSubjectAssetDetail = modelBuilder.Entity<LegalSubjectAssetDetail>();
            legalSubjectAssetDetail.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            legalSubjectAssetDetail.Property(c => c.IsDeleted).HasDefaultValue(false);
            legalSubjectAssetDetail.Property(cw => cw.isManualData).HasDefaultValue(false);
            legalSubjectAssetDetail.Property(cw => cw.type).HasDefaultValue("SHOP");

            //favoriteEntity
            var favoriteEntity = modelBuilder.Entity<Favorite>();
            favoriteEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            favoriteEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            favoriteEntity.Property(cw => cw.isManualData).HasDefaultValue(false);


            #endregion
        }

    }
}
