using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class product_order_verify_code : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "verifyCode",
                table: "shop_productOrders",
                maxLength: 20,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "verifyCode",
                table: "shop_productOrders");
        }
    }
}
