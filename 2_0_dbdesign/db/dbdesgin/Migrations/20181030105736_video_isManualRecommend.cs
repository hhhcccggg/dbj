using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class video_isManualRecommend : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<bool>(
                name: "isManualRecommend",
                table: "core_videos",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "isManualRecommend",
                table: "core_videos");
        }
    }
}
