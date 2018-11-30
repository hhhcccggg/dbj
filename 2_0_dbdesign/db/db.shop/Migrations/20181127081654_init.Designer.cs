﻿// <auto-generated />
using System;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Infrastructure;
using Microsoft.EntityFrameworkCore.Migrations;
using Microsoft.EntityFrameworkCore.Storage.ValueConversion;
using db.shop.Models;

namespace db.shop.Migrations
{
    [DbContext(typeof(ShopDbContext))]
    [Migration("20181127081654_init")]
    partial class init
    {
        protected override void BuildTargetModel(ModelBuilder modelBuilder)
        {
#pragma warning disable 612, 618
            modelBuilder
                .HasAnnotation("ProductVersion", "2.1.2-rtm-30932")
                .HasAnnotation("Relational:MaxIdentifierLength", 64);

            modelBuilder.Entity("db.shop.Models.o2o.OfflineStoreExtraService", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<long>("extraServiceId");

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("notes")
                        .HasMaxLength(1024);

                    b.Property<long>("offlineStoreId");

                    b.Property<int>("status")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0);

                    b.HasKey("Id");

                    b.ToTable("o2o_offlineStoreExtraServices");
                });

            modelBuilder.Entity("db.shop.Models.o2o.OfflineStoreOpeningHour", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<int>("closeTime");

                    b.Property<int>("day");

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<long>("offlineStoreId");

                    b.Property<int>("openTime");

                    b.HasKey("Id");

                    b.ToTable("o2o_offlineStoreOpeningHours");
                });

            modelBuilder.Entity("db.shop.Models.o2o.OfflineStoreServiceScope", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("notes")
                        .HasMaxLength(1024);

                    b.Property<long>("offlineStoreId");

                    b.Property<long>("serviceScopeId");

                    b.Property<int>("status")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0);

                    b.HasKey("Id");

                    b.ToTable("o2o_offlineStoreServiceScopes");
                });

            modelBuilder.Entity("db.shop.Models.o2o.OfflineStoreStaff", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<bool>("isSuperStar")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<long>("offlineStoreId");

                    b.Property<long>("userId");

                    b.HasKey("Id");

                    b.ToTable("o2o_offlineStoreStaffs");
                });

            modelBuilder.Entity("db.shop.Models.shop.BusinessSeller", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<string>("address")
                        .HasMaxLength(512);

                    b.Property<long>("categoryId");

                    b.Property<string>("categoryLevel");

                    b.Property<int>("cityId");

                    b.Property<string>("cityLevel")
                        .HasMaxLength(512);

                    b.Property<string>("contactName")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("contactPhone")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<string>("coverImages")
                        .HasMaxLength(1024);

                    b.Property<string>("description")
                        .HasMaxLength(2048);

                    b.Property<DateTime>("expireTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("expireTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<float>("grade")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0f);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<bool>("isReviewed")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<bool>("isStopService")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<float>("latitude")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0f);

                    b.Property<int>("level")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0);

                    b.Property<string>("logoUrl")
                        .HasMaxLength(512);

                    b.Property<float>("longitude")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0f);

                    b.Property<string>("mainConverImage")
                        .HasMaxLength(128);

                    b.Property<string>("marketName")
                        .HasMaxLength(128);

                    b.Property<string>("name")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<string>("qq")
                        .HasMaxLength(20);

                    b.Property<long>("recommendIndex")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0L);

                    b.Property<string>("sellerNumber")
                        .HasMaxLength(128);

                    b.Property<string>("shareDesc")
                        .HasMaxLength(512);

                    b.Property<int>("status")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0);

                    b.Property<string>("subName")
                        .HasMaxLength(128);

                    b.Property<int>("type")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0);

                    b.HasKey("Id");

                    b.ToTable("shop_businessSellers");
                });

            modelBuilder.Entity("db.shop.Models.shop.BusinessSellerReview", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<long>("businessSellerId");

                    b.Property<string>("identifyId")
                        .IsRequired()
                        .HasMaxLength(50);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("rejectMsg")
                        .HasMaxLength(512);

                    b.Property<string>("reviewData")
                        .IsRequired()
                        .HasMaxLength(1024);

                    b.Property<int>("status")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0);

                    b.Property<string>("title")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.HasKey("Id");

                    b.ToTable("shop_offlineStoreReviews");
                });

            modelBuilder.Entity("db.shop.Models.shop.DeliveryTemplate", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<int>("billType")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0);

                    b.Property<long>("deliveryTemplateScopeId");

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("name")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<long>("sellerId");

                    b.HasKey("Id");

                    b.ToTable("shop_deliveryTemplates");
                });

            modelBuilder.Entity("db.shop.Models.shop.DeliveryTemplateScope", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<string>("deliveryArea")
                        .IsRequired();

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<int>("item")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(1);

                    b.Property<float>("itemPrice")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(1f);

                    b.Property<int>("overItem")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(1);

                    b.Property<float>("overItemPrice")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(1f);

                    b.HasKey("Id");

                    b.ToTable("shop_deliveryTemplateScopes");
                });

            modelBuilder.Entity("db.shop.Models.shop.Product", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<long>("brandId");

                    b.Property<long>("categoryId");

                    b.Property<string>("categoryLevel")
                        .HasMaxLength(1024);

                    b.Property<long>("commentCount")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0L);

                    b.Property<long>("deliverytemplateId")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0L);

                    b.Property<string>("detailDescription");

                    b.Property<float>("grade")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0f);

                    b.Property<string>("imageUrls");

                    b.Property<long>("inventory")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0L);

                    b.Property<bool>("isJoinMemberDiscount")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<bool>("isNeedDelivery")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(true);

                    b.Property<bool>("isPublish")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("marketName")
                        .HasMaxLength(512);

                    b.Property<string>("name")
                        .IsRequired()
                        .HasMaxLength(128);

                    b.Property<string>("notes")
                        .HasMaxLength(512);

                    b.Property<string>("numberId")
                        .HasMaxLength(128);

                    b.Property<float>("priceDown")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0f);

                    b.Property<float>("priceUp")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0f);

                    b.Property<long>("productGroupId")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0L);

                    b.Property<int>("productType")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0);

                    b.Property<long>("sales")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0L);

                    b.Property<string>("searchName")
                        .HasMaxLength(512);

                    b.Property<long>("sellerId");

                    b.Property<string>("sellerPoint")
                        .HasMaxLength(512);

                    b.Property<string>("shareDesc")
                        .HasMaxLength(512);

                    b.Property<long>("specifyPublishTime")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0L);

                    b.Property<string>("subName")
                        .HasMaxLength(512);

                    b.Property<float>("universalDeliveryPrice")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(10f);

                    b.Property<string>("videoUrl")
                        .HasMaxLength(1024);

                    b.Property<float>("weight")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0f);

                    b.HasKey("Id");

                    b.ToTable("shop_products");
                });

            modelBuilder.Entity("db.shop.Models.shop.ProductAttri", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<long>("categoryId");

                    b.Property<long>("groupId");

                    b.Property<string>("identifyId")
                        .HasMaxLength(30);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("name")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<long>("parentId");

                    b.HasKey("Id");

                    b.ToTable("shop_productAttris");
                });

            modelBuilder.Entity("db.shop.Models.shop.ProductAttriLink", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<long>("attriId");

                    b.Property<long>("attriValueId");

                    b.Property<string>("extraData");

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<long>("productId");

                    b.HasKey("Id");

                    b.ToTable("shop_productAttriLinks");
                });

            modelBuilder.Entity("db.shop.Models.shop.ProductAttriValue", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<string>("attiValue")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<long>("productAttriId");

                    b.HasKey("Id");

                    b.ToTable("shop_productAttriValues");
                });

            modelBuilder.Entity("db.shop.Models.shop.ProductBrand", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<string>("description")
                        .HasMaxLength(1024);

                    b.Property<string>("imageUrl")
                        .HasMaxLength(512);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("name")
                        .IsRequired()
                        .HasMaxLength(20);

                    b.Property<int>("orderIndex")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0);

                    b.HasKey("Id");

                    b.ToTable("shop_productBrands");
                });

            modelBuilder.Entity("db.shop.Models.shop.ProductCart", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<long>("expireTime");

                    b.Property<string>("ip")
                        .HasMaxLength(50);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<long>("item");

                    b.Property<float>("price");

                    b.Property<long>("productId");

                    b.Property<long>("productskuId");

                    b.Property<long>("sellerId");

                    b.Property<string>("ua")
                        .HasMaxLength(128);

                    b.Property<long>("userId");

                    b.HasKey("Id");

                    b.ToTable("shop_productCarts");
                });

            modelBuilder.Entity("db.shop.Models.shop.ProductOrder", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<string>("buyerComment")
                        .HasMaxLength(512);

                    b.Property<bool>("buyerRate");

                    b.Property<DateTime?>("closeTime")
                        .HasColumnName("closeTime")
                        .HasColumnType("timestamp");

                    b.Property<string>("deliveryCode")
                        .HasMaxLength(128);

                    b.Property<float>("deliveryFee");

                    b.Property<string>("deliveryName")
                        .HasMaxLength(30);

                    b.Property<DateTime?>("deliveryTime")
                        .HasColumnName("deliveryTime")
                        .HasColumnType("timestamp");

                    b.Property<string>("deliveryType")
                        .HasMaxLength(30);

                    b.Property<DateTime?>("endTime")
                        .HasColumnName("endTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<float>("payment");

                    b.Property<DateTime?>("paymentTime")
                        .HasColumnName("paymentTime")
                        .HasColumnType("timestamp");

                    b.Property<int>("paymentType");

                    b.Property<long>("receiveAddressId");

                    b.Property<long>("sellerId");

                    b.Property<int>("status");

                    b.Property<DateTime?>("updateTime")
                        .HasColumnName("updateTime")
                        .HasColumnType("timestamp");

                    b.Property<long>("userId");

                    b.HasKey("Id");

                    b.ToTable("shop_productOrders");
                });

            modelBuilder.Entity("db.shop.Models.shop.ProductOrderItem", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<int>("num");

                    b.Property<long>("orderId");

                    b.Property<float>("price");

                    b.Property<long>("productId");

                    b.Property<long>("productskuId");

                    b.Property<string>("title")
                        .HasMaxLength(512);

                    b.Property<float>("totalFee");

                    b.HasKey("Id");

                    b.ToTable("shop_productOrderItems");
                });

            modelBuilder.Entity("db.shop.Models.shop.ProductSKU", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<string>("attrs");

                    b.Property<long>("inventory");

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<float>("originalPrice");

                    b.Property<long>("productId");

                    b.Property<float>("promotionPrice");

                    b.Property<long>("sales")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0L);

                    b.Property<string>("skuNumber")
                        .HasMaxLength(128);

                    b.Property<float>("weight");

                    b.HasKey("Id");

                    b.ToTable("shop_productSKUs");
                });

            modelBuilder.Entity("db.shop.Models.shop.ReceiveAddress", b =>
                {
                    b.Property<long>("Id")
                        .HasColumnName("id")
                        .HasMaxLength(128);

                    b.Property<DateTime>("CreateTime")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("createTime")
                        .HasColumnType("timestamp")
                        .HasDefaultValueSql("CURRENT_TIMESTAMP()");

                    b.Property<DateTime?>("DeleteTime")
                        .HasColumnName("deleteTime")
                        .HasColumnType("timestamp");

                    b.Property<bool>("IsDeleted")
                        .ValueGeneratedOnAdd()
                        .HasColumnName("isDeleted")
                        .HasDefaultValue(false);

                    b.Property<long>("cityId")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(0L);

                    b.Property<string>("cityLevel")
                        .HasMaxLength(512);

                    b.Property<bool>("isDefault")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<bool>("isManualData")
                        .ValueGeneratedOnAdd()
                        .HasDefaultValue(false);

                    b.Property<string>("receiverAddress")
                        .HasMaxLength(512);

                    b.Property<string>("receiverCity")
                        .HasMaxLength(30);

                    b.Property<string>("receiverCountry")
                        .HasMaxLength(30);

                    b.Property<string>("receiverMobile")
                        .HasMaxLength(30);

                    b.Property<string>("receiverName")
                        .IsRequired()
                        .HasMaxLength(30);

                    b.Property<string>("receiverPhone")
                        .HasMaxLength(30);

                    b.Property<string>("receiverZip")
                        .HasMaxLength(20);

                    b.Property<string>("reveiverState")
                        .HasMaxLength(30);

                    b.Property<DateTime?>("updateTime")
                        .HasColumnName("updateTime")
                        .HasColumnType("timestamp");

                    b.HasKey("Id");

                    b.ToTable("shop_receiveAddresses");
                });
#pragma warning restore 612, 618
        }
    }
}