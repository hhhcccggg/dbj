using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class order_add_receiveNameAndPhone : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "receiverMobile",
                table: "shop_productOrders",
                maxLength: 50,
                nullable: false,
                defaultValue: "");

            migrationBuilder.AddColumn<string>(
                name: "receiverName",
                table: "shop_productOrders",
                maxLength: 50,
                nullable: false,
                defaultValue: "");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "receiverMobile",
                table: "shop_productOrders");

            migrationBuilder.DropColumn(
                name: "receiverName",
                table: "shop_productOrders");
        }
    }
}
