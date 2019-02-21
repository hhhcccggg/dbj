ALTER TABLE `shop_productCashCoupons` ADD `appointment` varchar(128) NULL;

ALTER TABLE `shop_productCashCoupons` ADD `stackUse` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `shop_productCards` ADD `appointment` varchar(128) NULL;

ALTER TABLE `shop_productCards` ADD `stackUse` bit NOT NULL DEFAULT FALSE;

ALTER TABLE `core_users` ADD `fullName` varchar(128) NULL;

ALTER TABLE `core_users` ADD `notes` varchar(128) NULL;

ALTER TABLE `core_users` ADD `type` varchar(50) NULL DEFAULT 'NORMAL';

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190221024657_user_table', '2.1.4-rtm-31024');

