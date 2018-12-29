using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class tenants : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<string>(
                name: "type",
                table: "core_videos",
                maxLength: 50,
                nullable: false,
                defaultValue: "USER");

            migrationBuilder.AddColumn<long>(
                name: "tenantId",
                table: "core_users",
                nullable: true);

            migrationBuilder.AddColumn<long>(
                name: "tenantId",
                table: "core_userRoles",
                nullable: true);

            migrationBuilder.AddColumn<long>(
                name: "tenantId",
                table: "core_rolePermissions",
                nullable: true);

            migrationBuilder.AddColumn<float>(
                name: "rate",
                table: "core_comments",
                nullable: false,
                defaultValue: 0f);

            migrationBuilder.CreateTable(
                name: "core_comment_extraDatas",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    type = table.Column<string>(maxLength: 50, nullable: false, defaultValue: "VIDEO"),
                    commentId = table.Column<long>(nullable: false),
                    dataId = table.Column<long>(nullable: false, defaultValue: 0L),
                    dataContent = table.Column<string>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_comment_extraDatas", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_user_tenants",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    name = table.Column<string>(maxLength: 128, nullable: false),
                    identifyName = table.Column<string>(maxLength: 128, nullable: false),
                    legalSubjectId = table.Column<long>(nullable: false, defaultValue: 0L)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_user_tenants", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_core_user_tenants_identifyName",
                table: "core_user_tenants",
                column: "identifyName");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "core_comment_extraDatas");

            migrationBuilder.DropTable(
                name: "core_user_tenants");

            migrationBuilder.DropColumn(
                name: "type",
                table: "core_videos");

            migrationBuilder.DropColumn(
                name: "tenantId",
                table: "core_users");

            migrationBuilder.DropColumn(
                name: "tenantId",
                table: "core_userRoles");

            migrationBuilder.DropColumn(
                name: "tenantId",
                table: "core_rolePermissions");

            migrationBuilder.DropColumn(
                name: "rate",
                table: "core_comments");
        }
    }
}
