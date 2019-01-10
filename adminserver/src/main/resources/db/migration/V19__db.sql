CREATE TABLE `core_adBanners` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `type` varchar(128) NOT NULL,
    `platform` varchar(128) NOT NULL,
    `title` varchar(128) NOT NULL,
    `imageUrl` varchar(1024) NOT NULL,
    `refUrl` longtext NULL,
    `state` varchar(128) NOT NULL,
    CONSTRAINT `PK_core_adBanners` PRIMARY KEY (`id`)
);

CREATE TABLE `core_cities` (
    `id` bigint NOT NULL,
    `name` varchar(128) NOT NULL,
    `zipcode` varchar(128) NULL,
    `citycode` varchar(128) NULL,
    `longitude` float NOT NULL,
    `latitude` float NOT NULL,
    `level` varchar(128) NOT NULL,
    `parentId` bigint NOT NULL,
    CONSTRAINT `PK_core_cities` PRIMARY KEY (`id`)
);

CREATE TABLE `shop_Favorites` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `targetId` bigint NOT NULL,
    `targetType` varchar(128) NOT NULL,
    `userId` bigint NOT NULL,
    `title` varchar(1024) NOT NULL,
    `imageUrl` varchar(1024) NOT NULL,
    `price` int NOT NULL,
    CONSTRAINT `PK_shop_Favorites` PRIMARY KEY (`id`)
);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190110095259_db', '2.1.1-rtm-30846');

