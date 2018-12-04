using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class edit_index : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_core_videos_userId",
                table: "core_videos",
                column: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_core_userRoles_userId",
                table: "core_userRoles",
                column: "userId");

            migrationBuilder.CreateIndex(
                name: "IX_core_pets_userId",
                table: "core_pets",
                column: "userId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_core_videos_userId",
                table: "core_videos");

            migrationBuilder.DropIndex(
                name: "IX_core_userRoles_userId",
                table: "core_userRoles");

            migrationBuilder.DropIndex(
                name: "IX_core_pets_userId",
                table: "core_pets");
        }
    }
}
