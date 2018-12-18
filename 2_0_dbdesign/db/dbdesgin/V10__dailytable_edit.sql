ALTER TABLE `core_dailyIncreaseAnalysises` ADD `type` varchar(20) NOT NULL DEFAULT 'FAKE';

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181212092601_dailytable_edit', '2.1.1-rtm-30846');

