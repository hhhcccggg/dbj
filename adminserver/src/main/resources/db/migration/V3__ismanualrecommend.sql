ALTER TABLE `core_videos` ADD `isManualRecommend` bit NOT NULL DEFAULT FALSE;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181030105736_video_isManualRecommend', '2.1.1-rtm-30846');

