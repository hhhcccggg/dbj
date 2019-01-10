using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class analysis_field : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<long>(
                name: "perDau",
                table: "core_dailyIncreaseAnalysises",
                nullable: false,
                defaultValue: 0L);

            migrationBuilder.AddColumn<long>(
                name: "perMau",
                table: "core_dailyIncreaseAnalysises",
                nullable: false,
                defaultValue: 0L);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "perDau",
                table: "core_dailyIncreaseAnalysises");

            migrationBuilder.DropColumn(
                name: "perMau",
                table: "core_dailyIncreaseAnalysises");
        }
    }
}
