using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class userAsset : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "core_userAssets",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    coins = table.Column<long>(nullable: false),
                    remainBalance = table.Column<long>(nullable: false),
                    userId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_userAssets", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_userCoinDetails",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    title = table.Column<string>(maxLength: 512, nullable: false),
                    num = table.Column<int>(nullable: false),
                    extraData = table.Column<string>(maxLength: 1024, nullable: true),
                    type = table.Column<string>(maxLength: 20, nullable: false),
                    userId = table.Column<long>(nullable: false),
                    status = table.Column<string>(maxLength: 20, nullable: false),
                    statusMsg = table.Column<string>(maxLength: 1024, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_userCoinDetails", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_userCoinTypes",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    type = table.Column<string>(maxLength: 20, nullable: false),
                    coins = table.Column<long>(nullable: false),
                    userId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_userCoinTypes", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_core_categories_userId",
                table: "core_categories",
                column: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_core_userAssets_userId",
                table: "core_userAssets",
                column: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_core_userCoinDetails_userId",
                table: "core_userCoinDetails",
                column: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_core_userCoinTypes_userId",
                table: "core_userCoinTypes",
                column: "userId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "core_userAssets");

            migrationBuilder.DropTable(
                name: "core_userCoinDetails");

            migrationBuilder.DropTable(
                name: "core_userCoinTypes");

            migrationBuilder.DropIndex(
                name: "IX_core_categories_userId",
                table: "core_categories");
        }
    }
}
