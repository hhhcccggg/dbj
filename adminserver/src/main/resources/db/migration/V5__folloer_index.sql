CREATE INDEX `IX_core_followers_followerUserId` ON `core_followers` (`followerUserId`);

CREATE INDEX `IX_core_followers_userId` ON `core_followers` (`userId`);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181126071530_follower_index', '2.1.1-rtm-30846');

