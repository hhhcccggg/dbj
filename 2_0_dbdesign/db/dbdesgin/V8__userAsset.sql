CREATE TABLE `core_userAssets` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `coins` bigint NOT NULL,
    `remainBalance` bigint NOT NULL,
    `userId` bigint NOT NULL,
    CONSTRAINT `PK_core_userAssets` PRIMARY KEY (`id`)
);

CREATE TABLE `core_userCoinDetails` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `title` varchar(512) NOT NULL,
    `num` int NOT NULL,
    `extraData` varchar(1024) NULL,
    `type` varchar(20) NOT NULL,
    `userId` bigint NOT NULL,
    `status` varchar(20) NOT NULL,
    `statusMsg` varchar(1024) NULL,
    CONSTRAINT `PK_core_userCoinDetails` PRIMARY KEY (`id`)
);

CREATE TABLE `core_userCoinTypes` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `type` varchar(20) NOT NULL,
    `coins` bigint NOT NULL,
    `userId` bigint NOT NULL,
    CONSTRAINT `PK_core_userCoinTypes` PRIMARY KEY (`id`)
);

CREATE INDEX `IX_core_categories_userId` ON `core_categories` (`userId`);

CREATE INDEX `IX_core_userAssets_userId` ON `core_userAssets` (`userId`);

CREATE INDEX `IX_core_userCoinDetails_userId` ON `core_userCoinDetails` (`userId`);

CREATE INDEX `IX_core_userCoinTypes_userId` ON `core_userCoinTypes` (`userId`);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181205052531_userAsset', '2.1.1-rtm-30846');

