ALTER TABLE `shop_products` ADD `ruleDescription` longtext NULL;

INSERT INTO `__EFMigrationsHistory` (`MigrationId`, `ProductVersion`)
VALUES ('20190128091741_product_rule', '2.1.4-rtm-31024');

