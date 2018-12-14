using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class encashment : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<long>(
                name: "lockedCoins",
                table: "core_userCoinTypes",
                nullable: false,
                defaultValue: 0L);

            migrationBuilder.AddColumn<string>(
                name: "tradeNo",
                table: "core_userCoinDetails",
                maxLength: 128,
                nullable: true);

            migrationBuilder.AddColumn<string>(
                name: "tradeType",
                table: "core_userCoinDetails",
                maxLength: 50,
                nullable: true);

            migrationBuilder.CreateTable(
                name: "core_enCashAccounts",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    type = table.Column<string>(maxLength: 20, nullable: false),
                    uniqueId = table.Column<string>(maxLength: 128, nullable: false),
                    name = table.Column<string>(maxLength: 50, nullable: false),
                    avatarUrl = table.Column<string>(maxLength: 512, nullable: true),
                    accessToken = table.Column<string>(maxLength: 1024, nullable: true),
                    expireIn = table.Column<long>(nullable: false),
                    userId = table.Column<long>(nullable: false),
                    isLocked = table.Column<bool>(nullable: false, defaultValue: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_enCashAccounts", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_enCashMentDetails",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    userId = table.Column<long>(nullable: false),
                    coins = table.Column<int>(nullable: false),
                    rmbs = table.Column<int>(nullable: false),
                    payAccountId = table.Column<long>(nullable: false),
                    payAccountType = table.Column<string>(maxLength: 30, nullable: false),
                    isAllowedEnCash = table.Column<bool>(nullable: false, defaultValue: false),
                    status = table.Column<string>(maxLength: 30, nullable: false),
                    resultReason = table.Column<string>(maxLength: 512, nullable: true),
                    extraData = table.Column<string>(nullable: true),
                    tradeNo = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_enCashMentDetails", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_core_userCoinDetails_tradeNo",
                table: "core_userCoinDetails",
                column: "tradeNo");

            migrationBuilder.CreateIndex(
                name: "IX_core_enCashAccounts_userId",
                table: "core_enCashAccounts",
                column: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_core_enCashMentDetails_tradeNo",
                table: "core_enCashMentDetails",
                column: "tradeNo");

            migrationBuilder.CreateIndex(
                name: "IX_core_enCashMentDetails_userId",
                table: "core_enCashMentDetails",
                column: "userId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "core_enCashAccounts");

            migrationBuilder.DropTable(
                name: "core_enCashMentDetails");

            migrationBuilder.DropIndex(
                name: "IX_core_userCoinDetails_tradeNo",
                table: "core_userCoinDetails");

            migrationBuilder.DropColumn(
                name: "lockedCoins",
                table: "core_userCoinTypes");

            migrationBuilder.DropColumn(
                name: "tradeNo",
                table: "core_userCoinDetails");

            migrationBuilder.DropColumn(
                name: "tradeType",
                table: "core_userCoinDetails");
        }
    }
}
