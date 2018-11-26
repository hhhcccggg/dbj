using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class follower_index : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateIndex(
                name: "IX_core_followers_followerUserId",
                table: "core_followers",
                column: "followerUserId");

            migrationBuilder.CreateIndex(
                name: "IX_core_followers_userId",
                table: "core_followers",
                column: "userId");
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropIndex(
                name: "IX_core_followers_followerUserId",
                table: "core_followers");

            migrationBuilder.DropIndex(
                name: "IX_core_followers_userId",
                table: "core_followers");
        }
    }
}
