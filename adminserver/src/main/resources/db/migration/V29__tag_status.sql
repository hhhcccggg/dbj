ALTER TABLE `core_tags` ADD `status` int NOT NULL DEFAULT 0;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190228031225_tag_status', '2.1.4-rtm-31024');

