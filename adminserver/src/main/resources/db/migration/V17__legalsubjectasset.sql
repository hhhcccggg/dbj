ALTER TABLE `shop_products` ADD `supportCoin` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `shop_productOrders` ADD `actualPayment` int NOT NULL DEFAULT 0;

ALTER TABLE `shop_productOrders` ADD `couponids` varchar(1024) NULL;

ALTER TABLE `shop_productOrders` ADD `orderNo` varchar(512) NOT NULL DEFAULT '';

ALTER TABLE `shop_productOrders` ADD `useCoin` int NOT NULL DEFAULT 0;

CREATE TABLE `shop_legalSubjectAssetDetails` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `title` varchar(512) NOT NULL,
    `changeBalance` bigint NOT NULL,
    `currentBalance` bigint NOT NULL,
    `extraData` longtext NULL,
    `notes` varchar(1024) NULL,
    `type` varchar(128) NOT NULL DEFAULT 'SHOP',
    `orderId` bigint NULL,
    `legalSubjectId` bigint NOT NULL,
    CONSTRAINT `PK_shop_legalSubjectAssetDetails` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_legalSubjectAssets` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `remainBalance` bigint NOT NULL,
    `lockedBalance` bigint NOT NULL,
    `legalSubjectId` bigint NOT NULL,
    CONSTRAINT `PK_shop_legalSubjectAssets` PRIMARY KEY (`id`)
);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190105064745_legalsubjectasset', '2.1.1-rtm-30846');

