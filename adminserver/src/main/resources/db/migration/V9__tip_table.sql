ALTER TABLE `core_videos` ADD `tipCount` bigint NOT NULL DEFAULT 0;

CREATE TABLE `core_basic_buyCoinConfigs` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `coins` int NOT NULL,
    `rmbs` int NOT NULL,
    `title` varchar(30) NOT NULL,
    `orderIndex` int NOT NULL DEFAULT 0,
    CONSTRAINT `PK_core_basic_buyCoinConfigs` PRIMARY KEY (`id`)
);

CREATE TABLE `core_video_videoTipDetails` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `videoId` bigint NOT NULL,
    `userId` bigint NOT NULL,
    `tipCoin` bigint NOT NULL,
    CONSTRAINT `PK_core_video_videoTipDetails` PRIMARY KEY (`id`)
);

CREATE INDEX `IX_core_video_videoTipDetails_userId` ON `core_video_videoTipDetails` (`userId`);

CREATE INDEX `IX_core_video_videoTipDetails_videoId` ON `core_video_videoTipDetails` (`videoId`);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181205082407_tip_table', '2.1.1-rtm-30846');

