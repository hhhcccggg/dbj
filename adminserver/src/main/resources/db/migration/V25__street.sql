ALTER TABLE `shop_receiveAddresses` ADD `receiverStreet` varchar(50) NULL;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190215100004_receiveStreet', '2.1.4-rtm-31024');

