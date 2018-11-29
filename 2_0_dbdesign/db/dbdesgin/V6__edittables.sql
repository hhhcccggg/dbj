ALTER TABLE `core_videos` MODIFY COLUMN `title` varchar(512) NOT NULL;

ALTER TABLE `core_tags` ADD `desc` varchar(1024) NULL;

ALTER TABLE `core_tags` ADD `isHot` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_categories` ADD `status` int NOT NULL DEFAULT 0;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181129030345_edit_tables', '2.1.1-rtm-30846');

