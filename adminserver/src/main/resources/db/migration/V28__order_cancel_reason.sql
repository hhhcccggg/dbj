ALTER TABLE `shop_productOrders` ADD `cancelReason` varchar(512) NULL;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190227021629_order_cancel_reason', '2.1.4-rtm-31024');

