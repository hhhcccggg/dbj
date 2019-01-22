ALTER TABLE `core_users` ADD `recommendUserId` bigint NULL;

ALTER TABLE `core_userAssets` ADD `totalCoins` bigint NOT NULL DEFAULT 0;

CREATE TABLE `core_tasks` (
    `id` varchar(128) NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `type` varchar(128) NOT NULL,
    `title` varchar(512) NOT NULL,
    `coins` int NOT NULL,
    `desc` varchar(1024) NULL,
    CONSTRAINT `PK_core_tasks` PRIMARY KEY (`id`)
);

CREATE TABLE `core_userInvitations` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `initiatorUserId` bigint NOT NULL,
    `receivedUserId` bigint NULL,
    `state` varchar(128) NOT NULL,
    `message` varchar(512) NULL,
    CONSTRAINT `PK_core_userInvitations` PRIMARY KEY (`id`)
);

CREATE TABLE `core_userTasks` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `taskId` varchar(128) NOT NULL,
    `userId` bigint NOT NULL,
    `coins` int NOT NULL,
    `state` varchar(128) NOT NULL,
    `desc` varchar(1024) NULL,
    CONSTRAINT `PK_core_userTasks` PRIMARY KEY (`id`)
);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190114094247_miniapp', '2.1.4-rtm-31024');

