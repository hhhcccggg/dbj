using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class miniapp : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<long>(
                name: "recommendUserId",
                table: "core_users",
                nullable: true);

            migrationBuilder.AddColumn<long>(
                name: "totalCoins",
                table: "core_userAssets",
                nullable: false,
                defaultValue: 0L);

            migrationBuilder.CreateTable(
                name: "core_tasks",
                columns: table => new
                {
                    id = table.Column<string>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    type = table.Column<string>(maxLength: 128, nullable: false),
                    title = table.Column<string>(maxLength: 512, nullable: false),
                    coins = table.Column<int>(nullable: false),
                    desc = table.Column<string>(maxLength: 1024, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_tasks", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_userInvitations",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    initiatorUserId = table.Column<long>(nullable: false),
                    receivedUserId = table.Column<long>(nullable: true),
                    state = table.Column<string>(maxLength: 128, nullable: false),
                    message = table.Column<string>(maxLength: 512, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_userInvitations", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_userTasks",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    taskId = table.Column<string>(maxLength: 128, nullable: false),
                    userId = table.Column<long>(nullable: false),
                    coins = table.Column<int>(nullable: false),
                    state = table.Column<string>(maxLength: 128, nullable: false),
                    desc = table.Column<string>(maxLength: 1024, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_userTasks", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "core_tasks");

            migrationBuilder.DropTable(
                name: "core_userInvitations");

            migrationBuilder.DropTable(
                name: "core_userTasks");

            migrationBuilder.DropColumn(
                name: "recommendUserId",
                table: "core_users");

            migrationBuilder.DropColumn(
                name: "totalCoins",
                table: "core_userAssets");
        }
    }
}
