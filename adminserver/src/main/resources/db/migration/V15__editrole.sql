ALTER TABLE `core_roles` ADD `tenantId` bigint NULL;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181229055009_editrole', '2.1.1-rtm-30846');

