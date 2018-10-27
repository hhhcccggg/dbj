using System;
using Microsoft.EntityFrameworkCore.Metadata;
using Microsoft.EntityFrameworkCore.Migrations;

namespace dbdesgin.Migrations
{
    public partial class init : Migration
    {
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.CreateTable(
                name: "core_appVersions",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    version = table.Column<string>(maxLength: 30, nullable: true),
                    versionNum = table.Column<int>(nullable: false),
                    title = table.Column<string>(maxLength: 50, nullable: true),
                    description = table.Column<string>(maxLength: 1024, nullable: true),
                    platform = table.Column<int>(nullable: false),
                    downloadUrl = table.Column<string>(maxLength: 512, nullable: true),
                    upgradeType = table.Column<int>(nullable: false, defaultValue: 0)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_appVersions", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_categories",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    name = table.Column<string>(maxLength: 20, nullable: false),
                    parentId = table.Column<long>(nullable: false, defaultValue: 0L),
                    type = table.Column<int>(nullable: false, defaultValue: 0),
                    userId = table.Column<long>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_categories", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_comments",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    userId = table.Column<long>(nullable: false),
                    isOwner = table.Column<bool>(nullable: false, defaultValue: false),
                    contentTxt = table.Column<string>(maxLength: 1024, nullable: false),
                    refCommentId = table.Column<long>(nullable: false, defaultValue: 0L),
                    heartCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    resourceOwnerId = table.Column<long>(nullable: false),
                    resourceTypeId = table.Column<int>(nullable: false, defaultValue: 0),
                    reviewStatus = table.Column<string>(maxLength: 20, nullable: true),
                    reviewedResult = table.Column<string>(maxLength: 1024, nullable: true),
                    originContentTxt = table.Column<string>(maxLength: 1024, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_comments", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_complainReasons",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    title = table.Column<string>(maxLength: 256, nullable: true),
                    description = table.Column<string>(maxLength: 512, nullable: true),
                    type = table.Column<int>(nullable: false),
                    isOpen = table.Column<bool>(nullable: false, defaultValue: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_complainReasons", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_complains",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    fromTypeId = table.Column<int>(nullable: false, defaultValue: 0),
                    fromUserId = table.Column<long>(nullable: false),
                    toResId = table.Column<long>(nullable: false),
                    toResTypeId = table.Column<long>(nullable: false),
                    reasonId = table.Column<long>(nullable: false),
                    description = table.Column<string>(maxLength: 512, nullable: true),
                    snapshotUrl = table.Column<string>(maxLength: 512, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_complains", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_dailyIncreaseAnalysises",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    newUsers = table.Column<long>(nullable: false, defaultValue: 0L),
                    newVideos = table.Column<long>(nullable: false, defaultValue: 0L),
                    newOrders = table.Column<long>(nullable: false, defaultValue: 0L)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_dailyIncreaseAnalysises", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_followers",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    followerUserId = table.Column<long>(nullable: false),
                    userId = table.Column<long>(nullable: false),
                    isBoth = table.Column<bool>(nullable: false, defaultValue: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_followers", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_hearts",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    userId = table.Column<long>(nullable: false),
                    resourceOwnerId = table.Column<long>(nullable: false),
                    resourceTypeId = table.Column<int>(nullable: false, defaultValue: 0)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_hearts", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_livings",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    title = table.Column<string>(maxLength: 50, nullable: false),
                    coverUrl = table.Column<string>(maxLength: 512, nullable: true),
                    linkPets = table.Column<string>(nullable: true),
                    longitude = table.Column<float>(nullable: true),
                    latitude = table.Column<float>(nullable: true),
                    address = table.Column<string>(maxLength: 512, nullable: true),
                    status = table.Column<int>(nullable: false, defaultValue: 0),
                    rejectMsg = table.Column<string>(maxLength: 512, nullable: true),
                    recommendIndex = table.Column<int>(nullable: false, defaultValue: 0),
                    playCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    commentCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    heartCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    shareCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    onlinePeopleCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    userId = table.Column<long>(nullable: false),
                    pushUrl = table.Column<string>(maxLength: 512, nullable: true),
                    pullUrl = table.Column<string>(maxLength: 512, nullable: true),
                    hlsUrl = table.Column<string>(maxLength: 512, nullable: true),
                    liveingTotalTime = table.Column<long>(nullable: false, defaultValue: 0L),
                    getCoins = table.Column<long>(nullable: false, defaultValue: 0L),
                    getFriends = table.Column<long>(nullable: false, defaultValue: 0L),
                    isLiving = table.Column<bool>(nullable: false, defaultValue: false),
                    linkProductCount = table.Column<int>(nullable: false, defaultValue: 0),
                    complainCount = table.Column<int>(nullable: false, defaultValue: 0),
                    chatRoomId = table.Column<string>(maxLength: 512, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_livings", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_messageBroadcasts",
                columns: table => new
                {
                    receivedUserId = table.Column<long>(nullable: false)
                        .Annotation("MySql:ValueGenerationStrategy", MySqlValueGenerationStrategy.IdentityColumn),
                    lastMessageId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_messageBroadcasts", x => x.receivedUserId);
                });

            migrationBuilder.CreateTable(
                name: "core_messageDispatchs",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    refMessageId = table.Column<long>(nullable: false),
                    receivedUserId = table.Column<long>(nullable: false),
                    status = table.Column<int>(nullable: false, defaultValue: 0)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_messageDispatchs", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_messages",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    creatorUserId = table.Column<long>(nullable: false),
                    msgContent = table.Column<string>(maxLength: 1024, nullable: false),
                    dataContent = table.Column<string>(maxLength: 2048, nullable: true),
                    refUrl = table.Column<string>(maxLength: 1024, nullable: true),
                    messageType = table.Column<int>(nullable: false, defaultValue: 0)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_messages", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_musics",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    title = table.Column<string>(maxLength: 30, nullable: false),
                    artist = table.Column<string>(maxLength: 30, nullable: true),
                    coverUrl = table.Column<string>(maxLength: 512, nullable: true),
                    musicUrl = table.Column<string>(maxLength: 512, nullable: true),
                    duration = table.Column<float>(nullable: true),
                    categoryId = table.Column<long>(nullable: true),
                    recommendIndex = table.Column<int>(nullable: false, defaultValue: 0),
                    creatorUserId = table.Column<long>(nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_musics", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_permissions",
                columns: table => new
                {
                    name = table.Column<string>(maxLength: 50, nullable: false),
                    description = table.Column<string>(maxLength: 512, nullable: true, defaultValue: "")
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_permissions", x => x.name);
                });

            migrationBuilder.CreateTable(
                name: "core_pets",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    avatar = table.Column<string>(maxLength: 512, nullable: false),
                    nickName = table.Column<string>(maxLength: 20, nullable: false),
                    birthday = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    sex = table.Column<int>(nullable: false, defaultValue: 0),
                    categoryId = table.Column<long>(nullable: false, defaultValue: 0L),
                    userId = table.Column<long>(nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_pets", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_resourceNeedReviews",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    resContent = table.Column<string>(maxLength: 512, nullable: true),
                    resType = table.Column<int>(nullable: false, defaultValue: 0),
                    dataId = table.Column<long>(nullable: false, defaultValue: 0L),
                    dataType = table.Column<int>(nullable: false, defaultValue: 0),
                    reviewResultType = table.Column<string>(maxLength: 20, nullable: true),
                    reviewResultContent = table.Column<string>(maxLength: 1024, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_resourceNeedReviews", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_resourceRefGoods",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    resourceId = table.Column<long>(nullable: false),
                    resourceType = table.Column<int>(nullable: false, defaultValue: 0),
                    goods = table.Column<string>(maxLength: 1024, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_resourceRefGoods", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_rolePermissions",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    roleName = table.Column<string>(maxLength: 50, nullable: false),
                    permissionName = table.Column<string>(maxLength: 50, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_rolePermissions", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_roles",
                columns: table => new
                {
                    name = table.Column<string>(maxLength: 50, nullable: false),
                    description = table.Column<string>(maxLength: 512, nullable: true, defaultValue: "")
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_roles", x => x.name);
                });

            migrationBuilder.CreateTable(
                name: "core_tags",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    name = table.Column<string>(maxLength: 20, nullable: false),
                    recommendIndex = table.Column<int>(nullable: false, defaultValue: 0),
                    resNumber = table.Column<long>(nullable: false, defaultValue: 0L)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_tags", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_userDeviceTokens",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    userId = table.Column<long>(nullable: false, defaultValue: 0L),
                    deviceToken = table.Column<string>(maxLength: 512, nullable: true),
                    deviceType = table.Column<string>(maxLength: 20, nullable: true),
                    deviceName = table.Column<string>(maxLength: 50, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_userDeviceTokens", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_userRoles",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    userId = table.Column<long>(nullable: false),
                    roleName = table.Column<string>(maxLength: 50, nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_userRoles", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_users",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    username = table.Column<string>(maxLength: 110, nullable: false),
                    nickName = table.Column<string>(maxLength: 20, nullable: true),
                    avatarUrl = table.Column<string>(maxLength: 512, nullable: true),
                    password = table.Column<string>(maxLength: 512, nullable: true),
                    phone = table.Column<string>(maxLength: 20, nullable: true),
                    email = table.Column<string>(maxLength: 50, nullable: true),
                    birthday = table.Column<DateTime>(type: "timestamp", nullable: true),
                    sex = table.Column<int>(nullable: false, defaultValue: 0),
                    isLocked = table.Column<bool>(nullable: false, defaultValue: false),
                    isEmailVerification = table.Column<bool>(nullable: false, defaultValue: false),
                    isPhoneVerification = table.Column<bool>(nullable: false, defaultValue: false),
                    coins = table.Column<long>(nullable: false, defaultValue: 0L),
                    isLivingOpen = table.Column<bool>(nullable: false, defaultValue: false),
                    isLivingWatch = table.Column<bool>(nullable: false, defaultValue: false),
                    isLiving = table.Column<bool>(nullable: false, defaultValue: false),
                    livingId = table.Column<long>(nullable: false, defaultValue: 0L),
                    totalHearts = table.Column<long>(nullable: false, defaultValue: 0L),
                    totalFans = table.Column<long>(nullable: false, defaultValue: 0L),
                    totalMyFocuses = table.Column<long>(nullable: false, defaultValue: 0L),
                    isSuper = table.Column<bool>(nullable: false, defaultValue: false),
                    isReviewed = table.Column<bool>(nullable: false, defaultValue: false),
                    loginType = table.Column<int>(nullable: false, defaultValue: 1),
                    thirdOpenId = table.Column<string>(maxLength: 1024, nullable: true),
                    longitude = table.Column<float>(nullable: true),
                    latitude = table.Column<float>(nullable: true),
                    address = table.Column<string>(maxLength: 512, nullable: true),
                    complainCount = table.Column<int>(nullable: false, defaultValue: 0),
                    isManager = table.Column<bool>(nullable: false, defaultValue: false),
                    occupationId = table.Column<int>(nullable: false, defaultValue: 0),
                    loveStatusId = table.Column<int>(nullable: false, defaultValue: 0),
                    hxUserName = table.Column<string>(maxLength: 50, nullable: true),
                    hxPwd = table.Column<string>(maxLength: 512, nullable: true)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_users", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_userThirdAccountBinds",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    userId = table.Column<long>(nullable: false),
                    thirdOpenId = table.Column<string>(maxLength: 1024, nullable: true),
                    accountType = table.Column<int>(nullable: false),
                    accessToken = table.Column<string>(maxLength: 1024, nullable: true),
                    exipreIn = table.Column<long>(nullable: false),
                    nickName = table.Column<string>(maxLength: 128, nullable: true, defaultValue: "")
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_userThirdAccountBinds", x => x.id);
                });

            migrationBuilder.CreateTable(
                name: "core_videos",
                columns: table => new
                {
                    id = table.Column<long>(maxLength: 128, nullable: false),
                    createTime = table.Column<DateTime>(type: "timestamp", nullable: false, defaultValueSql: "CURRENT_TIMESTAMP()"),
                    isDeleted = table.Column<bool>(nullable: false, defaultValue: false),
                    deleteTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    title = table.Column<string>(maxLength: 50, nullable: false),
                    coverImageUrl = table.Column<string>(maxLength: 512, nullable: false),
                    coverImageWidth = table.Column<float>(nullable: false, defaultValue: 0f),
                    coverImageHeight = table.Column<float>(nullable: false, defaultValue: 0f),
                    firstFrameUrl = table.Column<string>(maxLength: 512, nullable: false),
                    firstFrameWidth = table.Column<float>(nullable: false, defaultValue: 0f),
                    firstFrameHeight = table.Column<float>(nullable: false, defaultValue: 0f),
                    videoUrl = table.Column<string>(maxLength: 512, nullable: false),
                    linkPets = table.Column<string>(nullable: true),
                    tags = table.Column<string>(nullable: true),
                    longitude = table.Column<float>(nullable: true),
                    latitude = table.Column<float>(nullable: true),
                    address = table.Column<string>(nullable: true),
                    isHiddenLocation = table.Column<bool>(nullable: false, defaultValue: false),
                    status = table.Column<int>(nullable: false, defaultValue: 1),
                    rejectMsg = table.Column<string>(maxLength: 512, nullable: true),
                    reviewUserId = table.Column<long>(nullable: true),
                    reviewTime = table.Column<DateTime>(type: "timestamp", nullable: true),
                    recommendIndex = table.Column<int>(nullable: false, defaultValue: 0),
                    playCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    commentCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    heartCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    shareCount = table.Column<long>(nullable: false, defaultValue: 0L),
                    userId = table.Column<long>(nullable: false),
                    musicId = table.Column<long>(nullable: true),
                    linkProductCount = table.Column<int>(nullable: false, defaultValue: 0),
                    complainCount = table.Column<int>(nullable: false, defaultValue: 0)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_core_videos", x => x.id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_core_users_email",
                table: "core_users",
                column: "email",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_core_users_phone",
                table: "core_users",
                column: "phone",
                unique: true);

            migrationBuilder.CreateIndex(
                name: "IX_core_users_username",
                table: "core_users",
                column: "username",
                unique: true);
        }

        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropTable(
                name: "core_appVersions");

            migrationBuilder.DropTable(
                name: "core_categories");

            migrationBuilder.DropTable(
                name: "core_comments");

            migrationBuilder.DropTable(
                name: "core_complainReasons");

            migrationBuilder.DropTable(
                name: "core_complains");

            migrationBuilder.DropTable(
                name: "core_dailyIncreaseAnalysises");

            migrationBuilder.DropTable(
                name: "core_followers");

            migrationBuilder.DropTable(
                name: "core_hearts");

            migrationBuilder.DropTable(
                name: "core_livings");

            migrationBuilder.DropTable(
                name: "core_messageBroadcasts");

            migrationBuilder.DropTable(
                name: "core_messageDispatchs");

            migrationBuilder.DropTable(
                name: "core_messages");

            migrationBuilder.DropTable(
                name: "core_musics");

            migrationBuilder.DropTable(
                name: "core_permissions");

            migrationBuilder.DropTable(
                name: "core_pets");

            migrationBuilder.DropTable(
                name: "core_resourceNeedReviews");

            migrationBuilder.DropTable(
                name: "core_resourceRefGoods");

            migrationBuilder.DropTable(
                name: "core_rolePermissions");

            migrationBuilder.DropTable(
                name: "core_roles");

            migrationBuilder.DropTable(
                name: "core_tags");

            migrationBuilder.DropTable(
                name: "core_userDeviceTokens");

            migrationBuilder.DropTable(
                name: "core_userRoles");

            migrationBuilder.DropTable(
                name: "core_users");

            migrationBuilder.DropTable(
                name: "core_userThirdAccountBinds");

            migrationBuilder.DropTable(
                name: "core_videos");
        }
    }
}
