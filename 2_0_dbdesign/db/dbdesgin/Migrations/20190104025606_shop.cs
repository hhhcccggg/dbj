using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class shop : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "o2o_offlineStoreExtraServices",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    storeId = table.Column<long>(nullable: false),
                    extraServiceId = table.Column<long>(nullable: false),
                    status = table.Column<int>(nullable: false, defaultValue: 0),
                    notes = table.Column<string>(maxLength: 1024, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_o2o_offlineStoreExtraServices", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "o2o_offlineStoreOpeningHours",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    day = table.Column<int>(nullable: false),
                    storeId = table.Column<long>(nullable: false),
                    openTime = table.Column<int>(nullable: false),
                    closeTime = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_o2o_offlineStoreOpeningHours", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "o2o_offlineStoreServiceScopes",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    storeId = table.Column<long>(nullable: false),
                    serviceScopeId = table.Column<long>(nullable: false),
                    notes = table.Column<string>(maxLength: 1024, nullable: true),
                    status = table.Column<int>(nullable: false, defaultValue: 0)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_o2o_offlineStoreServiceScopes", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "o2o_offlineStoreStaffs",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    storeId = table.Column<long>(nullable: false),
                    userId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_o2o_offlineStoreStaffs", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_deliveryTemplates",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    legalSubjectId = table.Column<long>(nullable: false),
                    storeId = table.Column<long>(nullable: false),
                    name = table.Column<string>(maxLength: 30, nullable: false),
                    billType = table.Column<int>(nullable: false, defaultValue: 0),
                    deliveryTemplateScopeId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_deliveryTemplates", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_deliveryTemplateScopes",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    deliveryArea = table.Column<string>(nullable: false),
                    item = table.Column<int>(nullable: false, defaultValue: 1),
                    itemPrice = table.Column<int>(nullable: false, defaultValue: 1),
                    overItem = table.Column<int>(nullable: false, defaultValue: 1),
                    overItemPrice = table.Column<int>(nullable: false, defaultValue: 1)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_deliveryTemplateScopes", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_discountCoupons",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    name = table.Column<string>(maxLength: 50, nullable: false),
                    couponCount = table.Column<int>(nullable: false),
                    discountType = table.Column<string>(maxLength: 128, nullable: false),
                    discountValue = table.Column<int>(nullable: false),
                    limitMoney = table.Column<int>(nullable: false),
                    limitGetPerPerson = table.Column<int>(nullable: false),
                    useInfo = table.Column<string>(nullable: true),
                    onlySupportOriginProduct = table.Column<bool>(nullable: false),
                    validStartTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    validEndTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    storeId = table.Column<long>(nullable: true),
                    legalSubjectId = table.Column<long>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_discountCoupons", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_legalSubjectReviews",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    identifyId = table.Column<string>(maxLength: 128, nullable: false),
                    title = table.Column<string>(maxLength: 128, nullable: false),
                    keyId = table.Column<string>(maxLength: 1024, nullable: true),
                    reviewData = table.Column<string>(nullable: false),
                    status = table.Column<int>(nullable: false),
                    rejectMsg = table.Column<string>(maxLength: 512, nullable: true),
                    legalSubjectId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_legalSubjectReviews", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_legalSubjects",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    name = table.Column<string>(maxLength: 128, nullable: false),
                    subName = table.Column<string>(maxLength: 128, nullable: true),
                    marketName = table.Column<string>(maxLength: 128, nullable: true),
                    description = table.Column<string>(maxLength: 2048, nullable: true),
                    corpId = table.Column<string>(maxLength: 512, nullable: true),
                    leagalRepresentativeName = table.Column<string>(maxLength: 128, nullable: false),
                    leagalRepresentativeID = table.Column<string>(maxLength: 512, nullable: false),
                    logoUrl = table.Column<string>(maxLength: 512, nullable: true),
                    contactAddress = table.Column<string>(maxLength: 1024, nullable: true),
                    regAddress = table.Column<string>(nullable: true),
                    cityId = table.Column<int>(nullable: false),
                    cityLevel = table.Column<string>(maxLength: 512, nullable: true),
                    contactName = table.Column<string>(maxLength: 50, nullable: false),
                    contactPhone = table.Column<string>(maxLength: 50, nullable: false),
                    qq = table.Column<string>(maxLength: 50, nullable: true),
                    wechat = table.Column<string>(maxLength: 50, nullable: true),
                    status = table.Column<int>(nullable: false, defaultValue: 1),
                    reviewed = table.Column<bool>(nullable: false, defaultValue: false),
                    reviewMsg = table.Column<string>(maxLength: 2048, nullable: true),
                    type = table.Column<string>(maxLength: 50, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_legalSubjects", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_productAttriLinks",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    productId = table.Column<long>(nullable: false),
                    attriId = table.Column<long>(nullable: false),
                    attriValueId = table.Column<long>(nullable: false),
                    extraData = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_productAttriLinks", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_productAttris",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    name = table.Column<string>(maxLength: 30, nullable: false),
                    identifyId = table.Column<string>(maxLength: 30, nullable: true),
                    categoryId = table.Column<long>(nullable: false),
                    parentId = table.Column<long>(nullable: false),
                    groupId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_productAttris", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_productAttriValues",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    attiValue = table.Column<string>(maxLength: 30, nullable: false),
                    productAttriId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_productAttriValues", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_productBrands",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    name = table.Column<string>(maxLength: 20, nullable: false),
                    imageUrl = table.Column<string>(maxLength: 512, nullable: true),
                    description = table.Column<string>(maxLength: 1024, nullable: true),
                    orderIndex = table.Column<int>(nullable: false, defaultValue: 0)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_productBrands", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_productCards",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    festivalCanUse = table.Column<bool>(nullable: false, defaultValue: true),
                    useInfo = table.Column<string>(maxLength: 1024, nullable: true),
                    validType = table.Column<string>(maxLength: 128, nullable: false),
                    specHoursValid = table.Column<int>(nullable: false),
                    validDays = table.Column<int>(nullable: false),
                    validStartTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    validEndTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    productId = table.Column<long>(nullable: false),
                    productSKUId = table.Column<long>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_productCards", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_productCarts",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    userId = table.Column<long>(nullable: false),
                    storeId = table.Column<long>(nullable: false),
                    productId = table.Column<long>(nullable: false),
                    productskuId = table.Column<long>(nullable: false),
                    item = table.Column<int>(nullable: false),
                    price = table.Column<int>(nullable: false),
                    ip = table.Column<string>(maxLength: 50, nullable: true),
                    ua = table.Column<string>(maxLength: 128, nullable: true),
                    expireTime = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_productCarts", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_productCashCoupons",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    couponValue = table.Column<int>(nullable: false),
                    festivalCanUse = table.Column<bool>(nullable: false, defaultValue: true),
                    useInfo = table.Column<string>(maxLength: 1024, nullable: true),
                    validType = table.Column<string>(maxLength: 128, nullable: false),
                    specHoursValid = table.Column<int>(nullable: false),
                    validDays = table.Column<int>(nullable: false),
                    validStartTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    validEndTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    productId = table.Column<long>(nullable: false),
                    productSKUId = table.Column<long>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_productCashCoupons", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_productOrderItems",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    productId = table.Column<long>(nullable: false),
                    productskuId = table.Column<long>(nullable: false),
                    orderId = table.Column<long>(nullable: false),
                    num = table.Column<int>(nullable: false),
                    title = table.Column<string>(maxLength: 512, nullable: true),
                    price = table.Column<int>(nullable: false),
                    totalFee = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_productOrderItems", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_productOrders",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    payment = table.Column<int>(nullable: false),
                    paymentType = table.Column<string>(maxLength: 128, nullable: false),
                    thirdPaymentTradeNo = table.Column<string>(maxLength: 512, nullable: false),
                    thirdPaymentTradeNotes = table.Column<string>(nullable: true),
                    deliveryFee = table.Column<int>(nullable: false),
                    status = table.Column<string>(maxLength: 128, nullable: false),
                    updateTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    paymentTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    deliveryTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    endTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    closeTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    deliveryName = table.Column<string>(maxLength: 128, nullable: true),
                    deliveryType = table.Column<string>(maxLength: 50, nullable: true),
                    deliveryCode = table.Column<string>(maxLength: 128, nullable: true),
                    userId = table.Column<long>(nullable: false),
                    storeId = table.Column<long>(nullable: false),
                    buyerComment = table.Column<string>(maxLength: 512, nullable: true),
                    buyerRate = table.Column<bool>(nullable: false),
                    receiveAddressId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_productOrders", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_products",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    productType = table.Column<int>(nullable: false, defaultValue: 0),
                    productDetailType = table.Column<string>(maxLength: 128, nullable: false),
                    numberId = table.Column<string>(maxLength: 128, nullable: true),
                    name = table.Column<string>(maxLength: 128, nullable: false),
                    subName = table.Column<string>(maxLength: 512, nullable: true),
                    searchName = table.Column<string>(maxLength: 512, nullable: true),
                    marketName = table.Column<string>(maxLength: 512, nullable: true),
                    sellerPoint = table.Column<string>(maxLength: 512, nullable: true),
                    categoryId = table.Column<long>(nullable: false),
                    categoryLevel = table.Column<string>(maxLength: 1024, nullable: true),
                    brandId = table.Column<long>(nullable: false),
                    shareDesc = table.Column<string>(maxLength: 512, nullable: true),
                    storeId = table.Column<long>(nullable: false),
                    commentCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    grade = table.Column<int>(nullable: false, defaultValue: 0),
                    sales = table.Column<long>(nullable: false, defaultValue: 0L),
                    inventory = table.Column<long>(nullable: false, defaultValue: 0L),
                    priceUp = table.Column<int>(nullable: false, defaultValue: 0),
                    priceDown = table.Column<int>(nullable: false, defaultValue: 0),
                    imageUrls = table.Column<string>(nullable: true),
                    videoUrl = table.Column<string>(maxLength: 1024, nullable: true),
                    productGroupId = table.Column<long>(nullable: false, defaultValue: 0L),
                    joinMemberDiscount = table.Column<bool>(nullable: false, defaultValue: false),
                    needDelivery = table.Column<bool>(nullable: false, defaultValue: true),
                    universalDeliveryPrice = table.Column<int>(nullable: false, defaultValue: 10),
                    deliverytemplateId = table.Column<long>(nullable: false, defaultValue: 0L),
                    publish = table.Column<bool>(nullable: false, defaultValue: false),
                    specifyPublishTime = table.Column<long>(nullable: false, defaultValue: 0L),
                    detailDescription = table.Column<string>(nullable: true),
                    weight = table.Column<int>(nullable: false, defaultValue: 0),
                    notes = table.Column<string>(maxLength: 512, nullable: true),
                    limitPerPerson = table.Column<int>(nullable: false, defaultValue: 0)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_products", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_productSKUs",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    skuNumber = table.Column<string>(maxLength: 128, nullable: true),
                    productId = table.Column<long>(nullable: false),
                    originalPrice = table.Column<int>(nullable: false),
                    promotionPrice = table.Column<int>(nullable: false),
                    inventory = table.Column<int>(nullable: false),
                    sales = table.Column<long>(nullable: false, defaultValue: 0L),
                    attrs = table.Column<string>(nullable: true),
                    weight = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_productSKUs", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_receiveAddresses",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    receiverName = table.Column<string>(maxLength: 50, nullable: false),
                    receiverPhone = table.Column<string>(maxLength: 50, nullable: true),
                    receiverMobile = table.Column<string>(maxLength: 50, nullable: true),
                    reveiverState = table.Column<string>(maxLength: 50, nullable: true),
                    receiverCity = table.Column<string>(maxLength: 50, nullable: true),
                    receiverCountry = table.Column<string>(maxLength: 50, nullable: true),
                    receiverAddress = table.Column<string>(maxLength: 512, nullable: true),
                    receiverZip = table.Column<string>(maxLength: 20, nullable: true),
                    cityId = table.Column<long>(nullable: false, defaultValue: 0L),
                    cityLevel = table.Column<string>(maxLength: 512, nullable: true),
                    updateTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    defaultAddr = table.Column<bool>(nullable: false, defaultValue: false),
                    userId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_receiveAddresses", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_stores",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    storeNumber = table.Column<string>(maxLength: 128, nullable: true),
                    name = table.Column<string>(maxLength: 50, nullable: false),
                    subName = table.Column<string>(maxLength: 128, nullable: true),
                    marketName = table.Column<string>(maxLength: 128, nullable: true),
                    shareDesc = table.Column<string>(maxLength: 512, nullable: true),
                    description = table.Column<string>(maxLength: 2048, nullable: true),
                    logoUrl = table.Column<string>(maxLength: 512, nullable: true),
                    recommendIndex = table.Column<long>(nullable: false, defaultValue: 0L),
                    longitude = table.Column<float>(nullable: false, defaultValue: 0f),
                    latitude = table.Column<float>(nullable: false, defaultValue: 0f),
                    address = table.Column<string>(maxLength: 512, nullable: true),
                    type = table.Column<string>(maxLength: 50, nullable: false, defaultValue: "0"),
                    level = table.Column<int>(nullable: false, defaultValue: 0),
                    categoryId = table.Column<long>(nullable: false),
                    categoryLevel = table.Column<string>(nullable: true),
                    cityId = table.Column<int>(nullable: false),
                    cityLevel = table.Column<string>(maxLength: 512, nullable: true),
                    expireTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    contactName = table.Column<string>(maxLength: 50, nullable: false),
                    contactPhone = table.Column<string>(maxLength: 20, nullable: false),
                    qq = table.Column<string>(maxLength: 20, nullable: true),
                    grade = table.Column<int>(nullable: false, defaultValue: 0),
                    status = table.Column<int>(nullable: false, defaultValue: 0),
                    reviewed = table.Column<bool>(nullable: false, defaultValue: false),
                    stopService = table.Column<bool>(nullable: false, defaultValue: false),
                    mainConverImage = table.Column<string>(maxLength: 128, nullable: true),
                    coverImages = table.Column<string>(maxLength: 1024, nullable: true),
                    legalSubjectId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_stores", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_userDiscountCoupons",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    couponId = table.Column<long>(nullable: false),
                    userId = table.Column<long>(nullable: false),
                    state = table.Column<string>(maxLength: 128, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_userDiscountCoupons", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_shop_legalSubjectReviews_identifyId",
                table: "shop_legalSubjectReviews",
                column: "identifyId");

            migrationBuilder.CreateIndex(
                name: "IX_shop_productOrders_status",
                table: "shop_productOrders",
                column: "status");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "o2o_offlineStoreExtraServices");

            migrationBuilder.DropTable(
                name: "o2o_offlineStoreOpeningHours");

            migrationBuilder.DropTable(
                name: "o2o_offlineStoreServiceScopes");

            migrationBuilder.DropTable(
                name: "o2o_offlineStoreStaffs");

            migrationBuilder.DropTable(
                name: "shop_deliveryTemplates");

            migrationBuilder.DropTable(
                name: "shop_deliveryTemplateScopes");

            migrationBuilder.DropTable(
                name: "shop_discountCoupons");

            migrationBuilder.DropTable(
                name: "shop_legalSubjectReviews");

            migrationBuilder.DropTable(
                name: "shop_legalSubjects");

            migrationBuilder.DropTable(
                name: "shop_productAttriLinks");

            migrationBuilder.DropTable(
                name: "shop_productAttris");

            migrationBuilder.DropTable(
                name: "shop_productAttriValues");

            migrationBuilder.DropTable(
                name: "shop_productBrands");

            migrationBuilder.DropTable(
                name: "shop_productCards");

            migrationBuilder.DropTable(
                name: "shop_productCarts");

            migrationBuilder.DropTable(
                name: "shop_productCashCoupons");

            migrationBuilder.DropTable(
                name: "shop_productOrderItems");

            migrationBuilder.DropTable(
                name: "shop_productOrders");

            migrationBuilder.DropTable(
                name: "shop_products");

            migrationBuilder.DropTable(
                name: "shop_productSKUs");

            migrationBuilder.DropTable(
                name: "shop_receiveAddresses");

            migrationBuilder.DropTable(
                name: "shop_stores");

            migrationBuilder.DropTable(
                name: "shop_userDiscountCoupons");
        }
    }
}
