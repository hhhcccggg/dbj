CREATE INDEX `IX_core_videos_userId` ON `core_videos` (`userId`);

CREATE INDEX `IX_core_userRoles_userId` ON `core_userRoles` (`userId`);

CREATE INDEX `IX_core_pets_userId` ON `core_pets` (`userId`);

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181204102729_edit_index', '2.1.1-rtm-30846');

