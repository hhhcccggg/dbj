using System;
using System.Collections.Generic;
using System.Text;
using dbdesgin.Models;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace dbdesgin.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options)
            : base(options)
        {
        }

        public DbSet<User> Users { get; set; }
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



            //Category
            var categoryEntity = modelBuilder.Entity<Category>();
            categoryEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            categoryEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            categoryEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            categoryEntity.Property(c => c.parentId).HasDefaultValue(0);
            categoryEntity.Property(c => c.type).HasDefaultValue(0);
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
            //Pet
            var petEntity = modelBuilder.Entity<Pet>();
            petEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            petEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            petEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            petEntity.Property(c => c.birthday).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            petEntity.Property(c => c.sex).HasDefaultValue(0);
            petEntity.Property(c => c.categoryId).HasDefaultValue(0);

            //Comment
            var commentEntity = modelBuilder.Entity<Comment>();
            commentEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            commentEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            commentEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            commentEntity.Property(c => c.heartCount).HasDefaultValue(0);
            commentEntity.Property(c => c.resourceTypeId).HasDefaultValue(0);
            commentEntity.Property(c => c.refCommentId).HasDefaultValue(0);
            commentEntity.Property(c => c.isOwner).HasDefaultValue(false);

            //Heart
            var heartEntity = modelBuilder.Entity<Heart>();
            heartEntity.Property(c => c.CreateTime).HasDefaultValueSql("CURRENT_TIMESTAMP()");
            heartEntity.Property(c => c.IsDeleted).HasDefaultValue(false);
            heartEntity.Property(cw => cw.isManualData).HasDefaultValue(false);
            heartEntity.Property(c => c.resourceTypeId).HasDefaultValue(0);

            //Follower
            var followerEntity = modelBuilder.Entity<Follower>();
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

            #region 权限
            // 角色
            var roleEntity = modelBuilder.Entity<Role>();
            roleEntity.Property(cw => cw.description).HasDefaultValue("");
            // 权限
            var permissionEntity = modelBuilder.Entity<Permission>();
            permissionEntity.Property(cw => cw.description).HasDefaultValue("");

            #endregion
        }

    }
}
