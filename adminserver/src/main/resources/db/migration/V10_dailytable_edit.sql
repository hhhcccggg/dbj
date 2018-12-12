ALTER TABLE `core_dailyIncreaseAnalysises` ADD `type` longtext NULL DEFAULT 'FAKE';

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181212090919_dailytable_edit', '2.1.1-rtm-30846');

