ALTER TABLE `shop_productOrders` ADD `receiverMobile` varchar(50) NOT NULL DEFAULT '';

ALTER TABLE `shop_productOrders` ADD `receiverName` varchar(50) NOT NULL DEFAULT '';

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190315022712_order_add_receiveNameAndPhone', '2.1.4-rtm-31024');

