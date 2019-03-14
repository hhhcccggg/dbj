ALTER TABLE `shop_productOrders` ADD `receiveAddress` longtext NULL;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190314011121_order_add_receiveAddress', '2.1.4-rtm-31024');

