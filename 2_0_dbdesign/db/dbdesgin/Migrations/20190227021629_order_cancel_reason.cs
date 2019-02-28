using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class order_cancel_reason : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "cancelReason",
                table: "shop_productOrders",
                maxLength: 512,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "cancelReason",
                table: "shop_productOrders");
        }
    }
}
