using System;
using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class db : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "core_adBanners",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    type = table.Column<string>(maxLength: 128, nullable: false),
                    platform = table.Column<string>(maxLength: 128, nullable: false),
                    title = table.Column<string>(maxLength: 128, nullable: false),
                    imageUrl = table.Column<string>(maxLength: 1024, nullable: false),
                    refUrl = table.Column<string>(maxLength: 2048, nullable: true),
                    state = table.Column<string>(maxLength: 128, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_adBanners", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_cities",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    name = table.Column<string>(maxLength: 128, nullable: false),
                    zipcode = table.Column<string>(maxLength: 128, nullable: true),
                    citycode = table.Column<string>(maxLength: 128, nullable: true),
                    longitude = table.Column<float>(nullable: false),
                    latitude = table.Column<float>(nullable: false),
                    level = table.Column<string>(maxLength: 128, nullable: false),
                    parentId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_cities", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "shop_Favorites",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    isManualData = table.Column<bool>(nullable: false, defaultValue: false),
                    targetId = table.Column<long>(nullable: false),
                    targetType = table.Column<string>(maxLength: 128, nullable: false),
                    userId = table.Column<long>(nullable: false),
                    title = table.Column<string>(maxLength: 1024, nullable: false),
                    imageUrl = table.Column<string>(maxLength: 1024, nullable: false),
                    price = table.Column<int>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_shop_Favorites", x => x.id);
                });
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "core_adBanners");

            migrationBuilder.DropTable(
                name: "core_cities");

            migrationBuilder.DropTable(
                name: "shop_Favorites");
        }
    }
}
