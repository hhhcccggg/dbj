ALTER TABLE `core_basic_buyCoinConfigs` ADD `productId` varchar(128) NULL;

ALTER TABLE `core_basic_buyCoinConfigs` ADD `type` varchar(20) NOT NULL DEFAULT 'ANDROID';

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20181220083648_editbuycoin', '2.1.1-rtm-30846');

