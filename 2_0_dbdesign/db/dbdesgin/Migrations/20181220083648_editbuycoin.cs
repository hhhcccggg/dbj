using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class editbuycoin : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "productId",
                table: "core_basic_buyCoinConfigs",
                maxLength: 128,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "type",
                table: "core_basic_buyCoinConfigs",
                maxLength: 20,
                nullable: false,
                defaultValue: "ANDROID");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "productId",
                table: "core_basic_buyCoinConfigs");

            migrationBuilder.DropColumn(
                name: "type",
                table: "core_basic_buyCoinConfigs");
        }
    }
}
