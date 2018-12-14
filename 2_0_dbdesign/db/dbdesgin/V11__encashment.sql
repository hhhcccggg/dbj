ALTER TABLE `core_userCoinTypes` ADD `lockedCoins` bigint NOT NULL DEFAULT 0;

ALTER TABLE `core_userCoinDetails` ADD `tradeNo` varchar(128) NULL;

ALTER TABLE `core_userCoinDetails` ADD `tradeType` varchar(50) NULL;

CREATE TABLE `core_enCashAccounts` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `type` varchar(20) NOT NULL,
    `uniqueId` varchar(128) NOT NULL,
    `name` varchar(50) NOT NULL,
    `avatarUrl` varchar(512) NULL,
    `accessToken` varchar(1024) NULL,
    `expireIn` bigint NOT NULL,
    `userId` bigint NOT NULL,
    `isLocked` bit NOT NULL DEFAULT FALSE,
    CONSTRAINT `PK_core_enCashAccounts` PRIMARY KEY (`id`)
);

CREATE TABLE `core_enCashMentDetails` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `userId` bigint NOT NULL,
    `coins` int NOT NULL,
    `rmbs` int NOT NULL,
    `payAccountId` bigint NOT NULL,
    `payAccountType` varchar(30) NOT NULL,
    `isAllowedEnCash` bit NOT NULL DEFAULT FALSE,
    `status` varchar(30) NOT NULL,
    `resultReason` varchar(512) NULL,
    `extraData` longtext NULL,
    `tradeNo` varchar(255) NULL,
    CONSTRAINT `PK_core_enCashMentDetails` PRIMARY KEY (`id`)
);

CREATE INDEX `IX_core_userCoinDetails_tradeNo` ON `core_userCoinDetails` (`tradeNo`);

CREATE INDEX `IX_core_enCashAccounts_userId` ON `core_enCashAccounts` (`userId`);

CREATE INDEX `IX_core_enCashMentDetails_tradeNo` ON `core_enCashMentDetails` (`tradeNo`);

CREATE INDEX `IX_core_enCashMentDetails_userId` ON `core_enCashMentDetails` (`userId`);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181214092230_encashment', '2.1.1-rtm-30846');

