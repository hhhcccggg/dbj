CREATE TABLE `__EFMigrationsHistory` (
    `MigrationId` varchar(95) NOT NULL,
    `ProductVersion` varchar(32) NOT NULL,
    CONSTRAINT `PK___EFMigrationsHistory` PRIMARY KEY (`MigrationId`)
);

CREATE TABLE `core_appVersions` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `version` varchar(30) NULL,
    `versionNum` int NOT NULL,
    `title` varchar(50) NULL,
    `description` varchar(1024) NULL,
    `platform` int NOT NULL,
    `downloadUrl` varchar(512) NULL,
    `upgradeType` int NOT NULL DEFAULT 0,
    CONSTRAINT `PK_core_appVersions` PRIMARY KEY (`id`)
);

CREATE TABLE `core_categories` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `name` varchar(20) NOT NULL,
    `parentId` bigint NOT NULL DEFAULT 0,
    `type` int NOT NULL DEFAULT 0,
    `userId` bigint NULL,
    CONSTRAINT `PK_core_categories` PRIMARY KEY (`id`)
);

CREATE TABLE `core_comments` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `userId` bigint NOT NULL,
    `isOwner` bit NOT NULL DEFAULT FALSE,
    `contentTxt` varchar(1024) NOT NULL,
    `refCommentId` bigint NOT NULL DEFAULT 0,
    `heartCount` bigint NOT NULL DEFAULT 0,
    `resourceOwnerId` bigint NOT NULL,
    `resourceTypeId` int NOT NULL DEFAULT 0,
    `reviewStatus` varchar(20) NULL,
    `reviewedResult` varchar(1024) NULL,
    `originContentTxt` varchar(1024) NULL,
    CONSTRAINT `PK_core_comments` PRIMARY KEY (`id`)
);

CREATE TABLE `core_complainReasons` (
    `id` bigint NOT NULL,
    `title` varchar(256) NULL,
    `description` varchar(512) NULL,
    `type` int NOT NULL,
    `isOpen` bit NOT NULL DEFAULT TRUE,
    CONSTRAINT `PK_core_complainReasons` PRIMARY KEY (`id`)
);

CREATE TABLE `core_complains` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `fromTypeId` int NOT NULL DEFAULT 0,
    `fromUserId` bigint NOT NULL,
    `toResId` bigint NOT NULL,
    `toResTypeId` bigint NOT NULL,
    `reasonId` bigint NOT NULL,
    `description` varchar(512) NULL,
    `snapshotUrl` varchar(512) NULL,
    CONSTRAINT `PK_core_complains` PRIMARY KEY (`id`)
);

CREATE TABLE `core_dailyIncreaseAnalysises` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `newUsers` bigint NOT NULL DEFAULT 0,
    `newVideos` bigint NOT NULL DEFAULT 0,
    `newOrders` bigint NOT NULL DEFAULT 0,
    CONSTRAINT `PK_core_dailyIncreaseAnalysises` PRIMARY KEY (`id`)
);

CREATE TABLE `core_followers` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `followerUserId` bigint NOT NULL,
    `userId` bigint NOT NULL,
    `isBoth` bit NOT NULL DEFAULT FALSE,
    CONSTRAINT `PK_core_followers` PRIMARY KEY (`id`)
);

CREATE TABLE `core_hearts` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `userId` bigint NOT NULL,
    `resourceOwnerId` bigint NOT NULL,
    `resourceTypeId` int NOT NULL DEFAULT 0,
    CONSTRAINT `PK_core_hearts` PRIMARY KEY (`id`)
);

CREATE TABLE `core_livings` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `title` varchar(50) NOT NULL,
    `coverUrl` varchar(512) NULL,
    `linkPets` longtext NULL,
    `longitude` float NULL,
    `latitude` float NULL,
    `address` varchar(512) NULL,
    `status` int NOT NULL DEFAULT 0,
    `rejectMsg` varchar(512) NULL,
    `recommendIndex` int NOT NULL DEFAULT 0,
    `playCount` bigint NOT NULL DEFAULT 0,
    `commentCount` bigint NOT NULL DEFAULT 0,
    `heartCount` bigint NOT NULL DEFAULT 0,
    `shareCount` bigint NOT NULL DEFAULT 0,
    `onlinePeopleCount` bigint NOT NULL DEFAULT 0,
    `userId` bigint NOT NULL,
    `pushUrl` varchar(512) NULL,
    `pullUrl` varchar(512) NULL,
    `hlsUrl` varchar(512) NULL,
    `liveingTotalTime` bigint NOT NULL DEFAULT 0,
    `getCoins` bigint NOT NULL DEFAULT 0,
    `getFriends` bigint NOT NULL DEFAULT 0,
    `isLiving` bit NOT NULL DEFAULT FALSE,
    `linkProductCount` int NOT NULL DEFAULT 0,
    `complainCount` int NOT NULL DEFAULT 0,
    `chatRoomId` varchar(512) NULL,
    CONSTRAINT `PK_core_livings` PRIMARY KEY (`id`)
);

CREATE TABLE `core_messageBroadcasts` (
    `receivedUserId` bigint NOT NULL AUTO_INCREMENT,
    `lastMessageId` bigint NOT NULL,
    CONSTRAINT `PK_core_messageBroadcasts` PRIMARY KEY (`receivedUserId`)
);

CREATE TABLE `core_messageDispatchs` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `refMessageId` bigint NOT NULL,
    `receivedUserId` bigint NOT NULL,
    `status` int NOT NULL DEFAULT 0,
    CONSTRAINT `PK_core_messageDispatchs` PRIMARY KEY (`id`)
);

CREATE TABLE `core_messages` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `creatorUserId` bigint NOT NULL,
    `msgContent` varchar(1024) NOT NULL,
    `dataContent` longtext NULL,
    `refUrl` varchar(1024) NULL,
    `messageType` int NOT NULL DEFAULT 0,
    CONSTRAINT `PK_core_messages` PRIMARY KEY (`id`)
);

CREATE TABLE `core_musics` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `title` varchar(30) NOT NULL,
    `artist` varchar(30) NULL,
    `coverUrl` varchar(512) NULL,
    `musicUrl` varchar(512) NULL,
    `duration` float NULL,
    `categoryId` bigint NULL,
    `recommendIndex` int NOT NULL DEFAULT 0,
    `creatorUserId` bigint NULL,
    CONSTRAINT `PK_core_musics` PRIMARY KEY (`id`)
);

CREATE TABLE `core_permissions` (
    `name` varchar(50) NOT NULL,
    `description` varchar(512) NULL DEFAULT '',
    CONSTRAINT `PK_core_permissions` PRIMARY KEY (`name`)
);

CREATE TABLE `core_pets` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `avatar` varchar(512) NOT NULL,
    `nickName` varchar(20) NOT NULL,
    `birthday` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `sex` int NOT NULL DEFAULT 0,
    `categoryId` bigint NOT NULL DEFAULT 0,
    `userId` bigint NOT NULL,
    CONSTRAINT `PK_core_pets` PRIMARY KEY (`id`)
);

CREATE TABLE `core_resourceNeedReviews` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `resContent` varchar(512) NULL,
    `resType` int NOT NULL DEFAULT 0,
    `dataId` bigint NOT NULL DEFAULT 0,
    `dataType` int NOT NULL DEFAULT 0,
    `reviewResultType` varchar(20) NULL,
    `reviewResultContent` varchar(1024) NULL,
    CONSTRAINT `PK_core_resourceNeedReviews` PRIMARY KEY (`id`)
);

CREATE TABLE `core_resourceRefGoods` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `resourceId` bigint NOT NULL,
    `resourceType` int NOT NULL DEFAULT 0,
    `goods` varchar(1024) NULL,
    CONSTRAINT `PK_core_resourceRefGoods` PRIMARY KEY (`id`)
);

CREATE TABLE `core_rolePermissions` (
    `id` bigint NOT NULL,
    `roleName` varchar(50) NOT NULL,
    `permissionName` varchar(50) NOT NULL,
    CONSTRAINT `PK_core_rolePermissions` PRIMARY KEY (`id`)
);

CREATE TABLE `core_roles` (
    `name` varchar(50) NOT NULL,
    `description` varchar(512) NULL DEFAULT '',
    CONSTRAINT `PK_core_roles` PRIMARY KEY (`name`)
);

CREATE TABLE `core_tags` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `name` varchar(20) NOT NULL,
    `recommendIndex` int NOT NULL DEFAULT 0,
    `resNumber` bigint NOT NULL DEFAULT 0,
    CONSTRAINT `PK_core_tags` PRIMARY KEY (`id`)
);

CREATE TABLE `core_userDeviceTokens` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `userId` bigint NOT NULL DEFAULT 0,
    `deviceToken` varchar(512) NULL,
    `deviceType` varchar(20) NULL,
    `deviceName` varchar(50) NULL,
    CONSTRAINT `PK_core_userDeviceTokens` PRIMARY KEY (`id`)
);

CREATE TABLE `core_userRoles` (
    `id` bigint NOT NULL,
    `userId` bigint NOT NULL,
    `roleName` varchar(50) NOT NULL,
    CONSTRAINT `PK_core_userRoles` PRIMARY KEY (`id`)
);

CREATE TABLE `core_users` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `username` varchar(110) NOT NULL,
    `nickName` varchar(20) NULL,
    `avatarUrl` varchar(512) NULL,
    `password` varchar(512) NULL,
    `phone` varchar(20) NULL,
    `email` varchar(50) NULL,
    `birthday` timestamp NULL,
    `sex` int NOT NULL DEFAULT 0,
    `isLocked` bit NOT NULL DEFAULT FALSE,
    `isEmailVerification` bit NOT NULL DEFAULT FALSE,
    `isPhoneVerification` bit NOT NULL DEFAULT FALSE,
    `coins` bigint NOT NULL DEFAULT 0,
    `isLivingOpen` bit NOT NULL DEFAULT FALSE,
    `isLivingWatch` bit NOT NULL DEFAULT FALSE,
    `isLiving` bit NOT NULL DEFAULT FALSE,
    `livingId` bigint NOT NULL DEFAULT 0,
    `totalHearts` bigint NOT NULL DEFAULT 0,
    `totalFans` bigint NOT NULL DEFAULT 0,
    `totalMyFocuses` bigint NOT NULL DEFAULT 0,
    `isSuper` bit NOT NULL DEFAULT FALSE,
    `isReviewed` bit NOT NULL DEFAULT FALSE,
    `loginType` int NOT NULL DEFAULT 1,
    `thirdOpenId` varchar(1024) NULL,
    `longitude` float NULL,
    `latitude` float NULL,
    `address` varchar(512) NULL,
    `complainCount` int NOT NULL DEFAULT 0,
    `isManager` bit NOT NULL DEFAULT FALSE,
    `occupationId` int NOT NULL DEFAULT 0,
    `loveStatusId` int NOT NULL DEFAULT 0,
    `hxUserName` varchar(50) NULL,
    `hxPwd` varchar(512) NULL,
    CONSTRAINT `PK_core_users` PRIMARY KEY (`id`)
);

CREATE TABLE `core_userThirdAccountBinds` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `userId` bigint NOT NULL,
    `thirdOpenId` varchar(1024) NULL,
    `accountType` int NOT NULL,
    `accessToken` varchar(1024) NULL,
    `exipreIn` bigint NOT NULL,
    `nickName` varchar(128) NULL DEFAULT '',
    CONSTRAINT `PK_core_userThirdAccountBinds` PRIMARY KEY (`id`)
);

CREATE TABLE `core_videos` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `title` varchar(50) NOT NULL,
    `coverImageUrl` varchar(512) NOT NULL,
    `coverImageWidth` float NOT NULL DEFAULT 0,
    `coverImageHeight` float NOT NULL DEFAULT 0,
    `firstFrameUrl` varchar(512) NOT NULL,
    `firstFrameWidth` float NOT NULL DEFAULT 0,
    `firstFrameHeight` float NOT NULL DEFAULT 0,
    `videoUrl` varchar(512) NOT NULL,
    `linkPets` longtext NULL,
    `tags` longtext NULL,
    `longitude` float NULL,
    `latitude` float NULL,
    `address` longtext NULL,
    `isHiddenLocation` bit NOT NULL DEFAULT FALSE,
    `status` int NOT NULL DEFAULT 1,
    `rejectMsg` varchar(512) NULL,
    `reviewUserId` bigint NULL,
    `reviewTime` timestamp NULL,
    `recommendIndex` int NOT NULL DEFAULT 0,
    `playCount` bigint NOT NULL DEFAULT 0,
    `commentCount` bigint NOT NULL DEFAULT 0,
    `heartCount` bigint NOT NULL DEFAULT 0,
    `shareCount` bigint NOT NULL DEFAULT 0,
    `userId` bigint NOT NULL,
    `musicId` bigint NULL,
    `linkProductCount` int NOT NULL DEFAULT 0,
    `complainCount` int NOT NULL DEFAULT 0,
    CONSTRAINT `PK_core_videos` PRIMARY KEY (`id`)
);

CREATE UNIQUE INDEX `IX_core_users_email` ON `core_users` (`email`);

CREATE UNIQUE INDEX `IX_core_users_phone` ON `core_users` (`phone`);

CREATE UNIQUE INDEX `IX_core_users_username` ON `core_users` (`username`);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181020032404_init', '2.1.1-rtm-30846');

