using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class user_sign_tagbackground : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "signature",
                table: "core_users",
                maxLength: 128,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "backgroundUrl",
                table: "core_tags",
                maxLength: 1024,
                nullable: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "signature",
                table: "core_users");

            migrationBuilder.DropColumn(
                name: "backgroundUrl",
                table: "core_tags");
        }
    }
}
