ALTER TABLE `core_dailyIncreaseAnalysises` ADD `perDau` bigint NOT NULL DEFAULT 0;

ALTER TABLE `core_dailyIncreaseAnalysises` ADD `perMau` bigint NOT NULL DEFAULT 0;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190110091334_analysis_field', '2.1.1-rtm-30846');

