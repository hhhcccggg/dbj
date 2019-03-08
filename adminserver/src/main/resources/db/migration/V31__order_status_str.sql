ALTER TABLE `shop_productOrders` ADD `statusStr` varchar(128) NOT NULL DEFAULT '';

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190308022809_order_status_str', '2.1.4-rtm-31024');

