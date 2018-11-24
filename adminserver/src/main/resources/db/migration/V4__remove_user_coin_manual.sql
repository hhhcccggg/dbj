ALTER TABLE `core_users` DROP COLUMN `coins`;

ALTER TABLE `core_videos` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_userThirdAccountBinds` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_users` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_userDeviceTokens` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_tags` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_resourceRefGoods` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_resourceNeedReviews` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_pets` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_musics` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_messages` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_messageDispatchs` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_livings` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_hearts` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_followers` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_dailyIncreaseAnalysises` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_complains` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_comments` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_categories` ADD `iconUrl` varchar(512) NULL;

ALTER TABLE `core_categories` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_categories` ADD `orderIndex` int NOT NULL DEFAULT 0;

ALTER TABLE `core_appVersions` ADD `isManualData` bit NOT NULL DEFAULT FALSE;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181119083831_remove_user_coin_manual', '2.1.1-rtm-30846');

