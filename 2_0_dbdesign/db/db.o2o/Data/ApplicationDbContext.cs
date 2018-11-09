using System;
using db.o2o.Models;
using Microsoft.EntityFrameworkCore;

namespace db.o2o.Data
{
    public class ApplicationDbContext : DbContext
    {
        public ApplicationDbContext(DbContextOptions<ApplicationDbContext> options):base(options) {
        }

        public DbSet<OfflineStore> OfflineStores { get; set; }
        public DbSet<OfflineStoreReview> OfflineStoreReviews { get; set; }
        public DbSet<OfflineStoreOpeningHour> OfflineStoreOpeningHours { get; set; }
        public DbSet<OfflineStoreServiceScope> GetOfflineStoreServiceScopes { get; set; }
        public DbSet<OfflineStoreExtraService> OfflineStoreExtraServices { get; set; }
        public DbSet<OfflineStoreStaff> OfflineStoreStaffs { get; set; }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            base.OnModelCreating(modelBuilder);
        }
    }
}
