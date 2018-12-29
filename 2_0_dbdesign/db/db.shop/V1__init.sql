CREATE TABLE `__EFMigrationsHistory` (
    `MigrationId` varchar(95) NOT NULL,
    `ProductVersion` varchar(32) NOT NULL,
    CONSTRAINT `PK___EFMigrationsHistory` PRIMARY KEY (`MigrationId`)
);

CREATE TABLE `core_legalSubjects` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `name` varchar(128) NOT NULL,
    `subName` varchar(128) NULL,
    `marketName` varchar(128) NULL,
    `description` longtext NULL,
    `corpId` varchar(512) NULL,
    `leagalRepresentativeName` varchar(128) NOT NULL,
    `leagalRepresentativeID` varchar(512) NOT NULL,
    `logoUrl` varchar(512) NULL,
    `contactAddress` varchar(1024) NULL,
    `regAddress` longtext NULL,
    `cityId` int NOT NULL,
    `cityLevel` varchar(512) NULL,
    `contactName` varchar(50) NOT NULL,
    `contactPhone` varchar(50) NOT NULL,
    `qq` varchar(50) NULL,
    `wechat` varchar(50) NULL,
    `status` int NOT NULL DEFAULT 1,
    `reviewed` bit NOT NULL DEFAULT FALSE,
    `reviewMsg` longtext NULL,
    `type` varchar(50) NOT NULL,
    CONSTRAINT `PK_core_legalSubjects` PRIMARY KEY (`id`)
);

CREATE TABLE `LegalSubjectReviews` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `identifyId` varchar(128) NOT NULL,
    `title` varchar(128) NOT NULL,
    `keyId` varchar(1024) NULL,
    `reviewData` longtext NOT NULL,
    `status` int NOT NULL,
    `rejectMsg` varchar(512) NULL,
    `legalSubjectId` bigint NOT NULL,
    CONSTRAINT `PK_LegalSubjectReviews` PRIMARY KEY (`id`)
);

CREATE TABLE `o2o_offlineStoreExtraServices` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `storeId` bigint NOT NULL,
    `extraServiceId` bigint NOT NULL,
    `status` int NOT NULL DEFAULT 0,
    `notes` varchar(1024) NULL,
    CONSTRAINT `PK_o2o_offlineStoreExtraServices` PRIMARY KEY (`id`)
);

CREATE TABLE `o2o_offlineStoreOpeningHours` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `day` int NOT NULL,
    `storeId` bigint NOT NULL,
    `openTime` int NOT NULL,
    `closeTime` int NOT NULL,
    CONSTRAINT `PK_o2o_offlineStoreOpeningHours` PRIMARY KEY (`id`)
);

CREATE TABLE `o2o_offlineStoreServiceScopes` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `storeId` bigint NOT NULL,
    `serviceScopeId` bigint NOT NULL,
    `notes` varchar(1024) NULL,
    `status` int NOT NULL DEFAULT 0,
    CONSTRAINT `PK_o2o_offlineStoreServiceScopes` PRIMARY KEY (`id`)
);

CREATE TABLE `o2o_offlineStoreStaffs` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `storeId` bigint NOT NULL,
    `userId` bigint NOT NULL,
    CONSTRAINT `PK_o2o_offlineStoreStaffs` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_deliveryTemplates` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `legalSubjectId` bigint NOT NULL,
    `storeId` bigint NOT NULL,
    `name` varchar(30) NOT NULL,
    `billType` int NOT NULL DEFAULT 0,
    `deliveryTemplateScopeId` bigint NOT NULL,
    CONSTRAINT `PK_shop_deliveryTemplates` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_deliveryTemplateScopes` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `deliveryArea` longtext NOT NULL,
    `item` int NOT NULL DEFAULT 1,
    `itemPrice` int NOT NULL DEFAULT 1,
    `overItem` int NOT NULL DEFAULT 1,
    `overItemPrice` int NOT NULL DEFAULT 1,
    CONSTRAINT `PK_shop_deliveryTemplateScopes` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_productAttriLinks` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `productId` bigint NOT NULL,
    `attriId` bigint NOT NULL,
    `attriValueId` bigint NOT NULL,
    `extraData` longtext NULL,
    CONSTRAINT `PK_shop_productAttriLinks` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_productAttris` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `name` varchar(30) NOT NULL,
    `identifyId` varchar(30) NULL,
    `categoryId` bigint NOT NULL,
    `parentId` bigint NOT NULL,
    `groupId` bigint NOT NULL,
    CONSTRAINT `PK_shop_productAttris` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_productAttriValues` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `attiValue` varchar(30) NOT NULL,
    `productAttriId` bigint NOT NULL,
    CONSTRAINT `PK_shop_productAttriValues` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_productBrands` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `name` varchar(20) NOT NULL,
    `imageUrl` varchar(512) NULL,
    `description` varchar(1024) NULL,
    `orderIndex` int NOT NULL DEFAULT 0,
    CONSTRAINT `PK_shop_productBrands` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_productCarts` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `userId` bigint NOT NULL,
    `storeId` bigint NOT NULL,
    `productId` bigint NOT NULL,
    `productskuId` bigint NOT NULL,
    `item` int NOT NULL,
    `price` int NOT NULL,
    `ip` varchar(50) NULL,
    `ua` varchar(128) NULL,
    `expireTime` bigint NOT NULL,
    CONSTRAINT `PK_shop_productCarts` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_productOrderItems` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `productId` bigint NOT NULL,
    `productskuId` bigint NOT NULL,
    `orderId` bigint NOT NULL,
    `num` int NOT NULL,
    `title` varchar(512) NULL,
    `price` int NOT NULL,
    `totalFee` int NOT NULL,
    CONSTRAINT `PK_shop_productOrderItems` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_productOrders` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `payment` int NOT NULL,
    `paymentType` varchar(128) NOT NULL,
    `deliveryFee` int NOT NULL,
    `status` varchar(128) NOT NULL,
    `updateTime` timestamp NULL,
    `paymentTime` timestamp NULL,
    `deliveryTime` timestamp NULL,
    `endTime` timestamp NULL,
    `closeTime` timestamp NULL,
    `deliveryName` varchar(128) NULL,
    `deliveryType` varchar(50) NULL,
    `deliveryCode` varchar(128) NULL,
    `userId` bigint NOT NULL,
    `storeId` bigint NOT NULL,
    `buyerComment` varchar(512) NULL,
    `buyerRate` bit NOT NULL,
    `receiveAddressId` bigint NOT NULL,
    CONSTRAINT `PK_shop_productOrders` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_products` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `productType` int NOT NULL DEFAULT 0,
    `numberId` varchar(128) NULL,
    `name` varchar(128) NOT NULL,
    `subName` varchar(512) NULL,
    `searchName` varchar(512) NULL,
    `marketName` varchar(512) NULL,
    `sellerPoint` varchar(512) NULL,
    `categoryId` bigint NOT NULL,
    `categoryLevel` varchar(1024) NULL,
    `brandId` bigint NOT NULL,
    `shareDesc` varchar(512) NULL,
    `storeId` bigint NOT NULL,
    `commentCount` bigint NOT NULL DEFAULT 0,
    `grade` int NOT NULL DEFAULT 0,
    `sales` bigint NOT NULL DEFAULT 0,
    `inventory` bigint NOT NULL DEFAULT 0,
    `priceUp` int NOT NULL DEFAULT 0,
    `priceDown` int NOT NULL DEFAULT 0,
    `imageUrls` longtext NULL,
    `videoUrl` varchar(1024) NULL,
    `productGroupId` bigint NOT NULL DEFAULT 0,
    `joinMemberDiscount` bit NOT NULL DEFAULT FALSE,
    `needDelivery` bit NOT NULL DEFAULT TRUE,
    `universalDeliveryPrice` int NOT NULL DEFAULT 10,
    `deliverytemplateId` bigint NOT NULL DEFAULT 0,
    `publish` bit NOT NULL DEFAULT FALSE,
    `specifyPublishTime` bigint NOT NULL DEFAULT 0,
    `detailDescription` longtext NULL,
    `weight` int NOT NULL DEFAULT 0,
    `notes` varchar(512) NULL,
    CONSTRAINT `PK_shop_products` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_productSKUs` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `skuNumber` varchar(128) NULL,
    `productId` bigint NOT NULL,
    `originalPrice` int NOT NULL,
    `promotionPrice` int NOT NULL,
    `inventory` int NOT NULL,
    `sales` bigint NOT NULL DEFAULT 0,
    `attrs` longtext NULL,
    `weight` int NOT NULL,
    CONSTRAINT `PK_shop_productSKUs` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_receiveAddresses` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `receiverName` varchar(50) NOT NULL,
    `receiverPhone` varchar(50) NULL,
    `receiverMobile` varchar(50) NULL,
    `reveiverState` varchar(50) NULL,
    `receiverCity` varchar(50) NULL,
    `receiverCountry` varchar(50) NULL,
    `receiverAddress` varchar(512) NULL,
    `receiverZip` varchar(20) NULL,
    `cityId` bigint NOT NULL DEFAULT 0,
    `cityLevel` varchar(512) NULL,
    `updateTime` timestamp NULL,
    `defaultAddr` bit NOT NULL DEFAULT FALSE,
    `userId` bigint NOT NULL,
    CONSTRAINT `PK_shop_receiveAddresses` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_stores` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `storeNumber` varchar(128) NULL,
    `name` varchar(50) NOT NULL,
    `subName` varchar(128) NULL,
    `marketName` varchar(128) NULL,
    `shareDesc` varchar(512) NULL,
    `description` longtext NULL,
    `logoUrl` varchar(512) NULL,
    `recommendIndex` bigint NOT NULL DEFAULT 0,
    `longitude` float NOT NULL DEFAULT 0,
    `latitude` float NOT NULL DEFAULT 0,
    `address` varchar(512) NULL,
    `type` varchar(50) NOT NULL DEFAULT '0',
    `level` int NOT NULL DEFAULT 0,
    `categoryId` bigint NOT NULL,
    `categoryLevel` longtext NULL,
    `cityId` int NOT NULL,
    `cityLevel` varchar(512) NULL,
    `expireTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `contactName` varchar(50) NOT NULL,
    `contactPhone` varchar(20) NOT NULL,
    `qq` varchar(20) NULL,
    `grade` int NOT NULL DEFAULT 0,
    `status` int NOT NULL DEFAULT 0,
    `reviewed` bit NOT NULL DEFAULT FALSE,
    `stopService` bit NOT NULL DEFAULT FALSE,
    `mainConverImage` varchar(128) NULL,
    `coverImages` varchar(1024) NULL,
    `legalSubjectId` bigint NOT NULL,
    CONSTRAINT `PK_shop_stores` PRIMARY KEY (`id`)
);

CREATE INDEX `IX_LegalSubjectReviews_identifyId` ON `LegalSubjectReviews` (`identifyId`);

CREATE INDEX `IX_shop_productOrders_status` ON `shop_productOrders` (`status`);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181229013941_init', '2.1.2-rtm-30932');

