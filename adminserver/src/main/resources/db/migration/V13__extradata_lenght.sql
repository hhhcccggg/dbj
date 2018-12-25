ALTER TABLE `core_userCoinDetails` MODIFY COLUMN `extraData` longtext NULL;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181225030821_extraData', '2.1.1-rtm-30846');

