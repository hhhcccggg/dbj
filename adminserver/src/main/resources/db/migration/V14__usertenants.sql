ALTER TABLE `core_videos` ADD `type` varchar(50) NOT NULL DEFAULT 'USER';

ALTER TABLE `core_users` ADD `tenantId` bigint NULL;

ALTER TABLE `core_userRoles` ADD `tenantId` bigint NULL;

ALTER TABLE `core_rolePermissions` ADD `tenantId` bigint NULL;

ALTER TABLE `core_comments` ADD `rate` float NOT NULL DEFAULT 0;

CREATE TABLE `core_comment_extraDatas` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `type` varchar(50) NOT NULL DEFAULT 'VIDEO',
    `commentId` bigint NOT NULL,
    `dataId` bigint NOT NULL DEFAULT 0,
    `dataContent` longtext NULL,
    CONSTRAINT `PK_core_comment_extraDatas` PRIMARY KEY (`id`)
);

CREATE TABLE `core_user_tenants` (
    `id` bigint NOT NULL,
    `createTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    `isDeleted` bit NOT NULL DEFAULT FALSE,
    `deleteTime` timestamp NULL,
    `isManualData` bit NOT NULL DEFAULT FALSE,
    `name` varchar(128) NOT NULL,
    `identifyName` varchar(128) NOT NULL,
    `legalSubjectId` bigint NOT NULL DEFAULT 0,
    CONSTRAINT `PK_core_user_tenants` PRIMARY KEY (`id`)
);

CREATE INDEX `IX_core_user_tenants_identifyName` ON `core_user_tenants` (`identifyName`);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181229030043_tenants', '2.1.1-rtm-30846');

