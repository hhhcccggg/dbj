using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class legalsubjectasset : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "supportCoin",
                table: "shop_products",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<int>(
                name: "actualPayment",
                table: "shop_productOrders",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<string>(
                name: "couponids",
                table: "shop_productOrders",
                maxLength: 1024,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "orderNo",
                table: "shop_productOrders",
                maxLength: 512,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<int>(
                name: "useCoin",
                table: "shop_productOrders",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "shop_legalSubjectAssetDetails",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    title = table.Column<string>(maxLength: 512, nullable: false),
                    changeBalance = table.Column<long>(nullable: false),
                    currentBalance = table.Column<long>(nullable: false),
                    extraData = table.Column<string>(nullable: true),
                    notes = table.Column<string>(maxLength: 1024, nullable: true),
                    type = table.Column<string>(maxLength: 128, nullable: false, defaultValue: "SHOP"),
                    orderId = table.Column<long>(nullable: true),
                    legalSubjectId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_legalSubjectAssetDetails", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_legalSubjectAssets",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    remainBalance = table.Column<long>(nullable: false),
                    lockedBalance = table.Column<long>(nullable: false),
                    legalSubjectId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_legalSubjectAssets", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "shop_legalSubjectAssetDetails");

            migrationBuilder.DropTable(
                name: "shop_legalSubjectAssets");

            migrationBuilder.DropColumn(
                name: "supportCoin",
                table: "shop_products");

            migrationBuilder.DropColumn(
                name: "actualPayment",
                table: "shop_productOrders");

            migrationBuilder.DropColumn(
                name: "couponids",
                table: "shop_productOrders");

            migrationBuilder.DropColumn(
                name: "orderNo",
                table: "shop_productOrders");

            migrationBuilder.DropColumn(
                name: "useCoin",
                table: "shop_productOrders");
        }
    }
}
