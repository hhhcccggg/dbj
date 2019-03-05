ALTER TABLE `core_users` ADD `signature` varchar(128) NULL;

ALTER TABLE `core_tags` ADD `backgroundUrl` varchar(1024) NULL;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190305032939_user_sign_tagbackground', '2.1.4-rtm-31024');

