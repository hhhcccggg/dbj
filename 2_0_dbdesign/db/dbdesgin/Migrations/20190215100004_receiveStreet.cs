using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class receiveStreet : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "receiverStreet",
                table: "shop_receiveAddresses",
                maxLength: 50,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "receiverStreet",
                table: "shop_receiveAddresses");
        }
    }
}
