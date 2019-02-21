using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class user_table : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "appointment",
                table: "shop_productCashCoupons",
                maxLength: 128,
                nullable: true);

            migrationBuilder.AddColumn<bool>(
                name: "stackUse",
                table: "shop_productCashCoupons",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<string>(
                name: "appointment",
                table: "shop_productCards",
                maxLength: 128,
                nullable: true);

            migrationBuilder.AddColumn<bool>(
                name: "stackUse",
                table: "shop_productCards",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<string>(
                name: "fullName",
                table: "core_users",
                maxLength: 128,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "notes",
                table: "core_users",
                maxLength: 128,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "type",
                table: "core_users",
                maxLength: 50,
                nullable: true,
                defaultValue: "NORMAL");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "appointment",
                table: "shop_productCashCoupons");

            migrationBuilder.DropColumn(
                name: "stackUse",
                table: "shop_productCashCoupons");

            migrationBuilder.DropColumn(
                name: "appointment",
                table: "shop_productCards");

            migrationBuilder.DropColumn(
                name: "stackUse",
                table: "shop_productCards");

            migrationBuilder.DropColumn(
                name: "fullName",
                table: "core_users");

            migrationBuilder.DropColumn(
                name: "notes",
                table: "core_users");

            migrationBuilder.DropColumn(
                name: "type",
                table: "core_users");
        }
    }
}
