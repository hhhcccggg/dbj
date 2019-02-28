ALTER TABLE `shop_productOrders` ADD `verifyCode` varchar(20) NULL;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190226094800_product_order_verify_code', '2.1.4-rtm-31024');

