using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class tip_table : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<long>(
                name: "tipCount",
                table: "core_videos",
                nullable: false,
                defaultValue: 0L);

            migrationBuilder.CreateTable(
                name: "core_basic_buyCoinConfigs",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    coins = table.Column<int>(nullable: false),
                    rmbs = table.Column<int>(nullable: false),
                    title = table.Column<string>(maxLength: 30, nullable: false),
                    orderIndex = table.Column<int>(nullable: false, defaultValue: 0)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_basic_buyCoinConfigs", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_video_videoTipDetails",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    videoId = table.Column<long>(nullable: false),
                    userId = table.Column<long>(nullable: false),
                    tipCoin = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_video_videoTipDetails", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_core_video_videoTipDetails_userId",
                table: "core_video_videoTipDetails",
                column: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_core_video_videoTipDetails_videoId",
                table: "core_video_videoTipDetails",
                column: "videoId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "core_basic_buyCoinConfigs");

            migrationBuilder.DropTable(
                name: "core_video_videoTipDetails");

            migrationBuilder.DropColumn(
                name: "tipCount",
                table: "core_videos");
        }
    }
}
