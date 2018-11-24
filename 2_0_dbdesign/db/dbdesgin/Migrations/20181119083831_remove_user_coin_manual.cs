using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class remove_user_coin_manual : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "coins",
                table: "core_users");

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_videos",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_userThirdAccountBinds",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_users",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_userDeviceTokens",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_tags",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_resourceRefGoods",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_resourceNeedReviews",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_pets",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_musics",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_messages",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_messageDispatchs",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_livings",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_hearts",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_followers",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_dailyIncreaseAnalysises",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_complains",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_comments",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<string>(
                name: "iconUrl",
                table: "core_categories",
                maxLength: 512,
                nullable: true);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_categories",
                nullable: false,
                defaultValue: false);

            migrationBuilder.AddColumn<int>(
                name: "orderIndex",
                table: "core_categories",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<bool>(
                name: "isManualData",
                table: "core_appVersions",
                nullable: false,
                defaultValue: false);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_videos");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_userThirdAccountBinds");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_users");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_userDeviceTokens");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_tags");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_resourceRefGoods");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_resourceNeedReviews");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_pets");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_musics");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_messages");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_messageDispatchs");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_livings");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_hearts");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_followers");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_dailyIncreaseAnalysises");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_complains");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_comments");

            migrationBuilder.DropColumn(
                name: "iconUrl",
                table: "core_categories");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_categories");

            migrationBuilder.DropColumn(
                name: "orderIndex",
                table: "core_categories");

            migrationBuilder.DropColumn(
                name: "isManualData",
                table: "core_appVersions");

            migrationBuilder.AddColumn<long>(
                name: "coins",
                table: "core_users",
                nullable: false,
                defaultValue: 0L);
        }
    }
}
