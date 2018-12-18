using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class dailytable_edit : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "type",
                table: "core_dailyIncreaseAnalysises",
                maxLength: 20,
                nullable: false,
                defaultValue: "FAKE");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "type",
                table: "core_dailyIncreaseAnalysises");
        }
    }
}
