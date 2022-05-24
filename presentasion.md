
##Μοντελα
(package: com.steft.travel_app.model)

Τα μοντέλα αναπαριστούν τα δεδομένα όπως αποθηκεύονται στις βάσεις (Sqlite και Firestore).

###Location (Προορισμός) - Sqlite
```kotlin
@Entity(
    tableName = "location", indices = [Index(value = ["city", "country"])],
    foreignKeys = [
        ForeignKey(
            entity = TravelAgency::class,
            parentColumns = ["id"],
            childColumns = ["travel_agency"],
            onDelete = ForeignKey.RESTRICT)])
data class Location(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "travel_agency")val travelAgency: UUID?,
    val city: Name,
    val country: Name
```
Ο προορισμός αποτελεί μια διακριτή περιοχή στον κόσμο, στην οποία μπορούν τα ταξιδιωτικά γραφεία να προσφέρουν εκδρομές. Τον χαρακτηρίζουν 4 πεδία:
* `id: UUID` - Η μοναδική ταυτότητα της εγγραφής, τύπου `UUID` (Universal Unique Identifier).
* `travelAgency: UUID` - Ξένο κλειδί προς μία εγγραφή του πίνακα `travel_agency`. Το πεδίο είναι nullable, όπως προσδιορίζει το Αγγλικό ερωτηματικό. Η ύπαρξη αυτού του πεδίου σε μια εγγραφή προσδιορίζει πως ο συγκεκριμένος προορισμός δημιουργήθηκε απο ένα ταξιδιωτικό γραφείο και δεν είναι διαθέσιμος σε όλα (εκτός φυσικά αν δημιουργήσουν τα ίδια εναν αντίστοιχο δικό τους).
* `city: Name` - Το όνομα της πόλης του προορισμού, τύπου `Name`, βλεπε(#anchor! Validation).
* `country: Name` - Το όνομα της πόλης του προορισμού, τύπου `Name`, βλεπε(#anchor! Validation). 

###TravelAgency (Ταξιδιωτικό Γραφείο) - Sqlite
```kotlin
@Entity(tableName = "travel_agency", indices = [Index(value = ["username"], unique = true)])
data class TravelAgency(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    val name: Name,
    val address: Address,
    val username: Username,
    val password: Sha256)
```
Αναπαριστά ένα ταξιδιωτικό γραφείο που χρησιμοποιεί την εφαρμογή μέσω ενός μοναδικού χρήστη, προσδιοριζόμενος από username/password, για να δημιουργεί και να διαχειρίζεται αγγελίες εκδρομών που διαθέτει. Το χαρακτηρίζουν 5 πεδία:
* `id: UUID` - Η μοναδική ταυτότητα της εγγραφής, τύπου `UUID` (Universal Unique Identifier).
* `name: Name` - Το όνομα του ταξιδιωτικού γραφείου, τύπου `Name`, βλεπε(#anchor! Validation).
* `address: Address` - Η διεύθυνση του ταξιδιωτικού γραφείου, τύπου `Address`, βλεπε(#anchor! Validation).
* `username: Userame` - Το μοναδικό όνομα χρήστη, τύπου `Username`, βλεπε(#anchor! Validation).
* `password: Password` - Ο κρυπτογραφημένος με SHA256 κρυπτογράφηση κωδικός του χρήστη, βλεπε(#anchor! Validation).

###Bundle (Πακέτο) - Sqlite
```kotlin
@Entity(
    tableName = "bundle",
    foreignKeys = [
        ForeignKey(
            entity = TravelAgency::class,
            parentColumns = ["id"],
            childColumns = ["travel_agency"],
            onDelete = ForeignKey.RESTRICT),
        ForeignKey(
            entity = Location::class,
            parentColumns = ["id"],
            childColumns = ["location"],
            onDelete = ForeignKey.RESTRICT)])
data class Bundle(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "travel_agency") val travelAgency: UUID,
    val location: UUID,
    val date: Date,
    val price: Double,
    val duration: Int,
    val hotels: List<Name>,
    val type: LocationType)
```
Αναπαριστά ένα πακέτο που προσφέρει ένα ταξιδιωτικό γραφείο σε μία περιοχή, για ένα χρονικό διάστημα, με μία συγκεκριμένη τιμή και προτεινόμενα ξενοδοχεία. Το χαρακτηρίζουν 8 πεδία:
* `id: UUID` - Η μοναδική ταυτότητα της εγγραφής, τύπου `UUID` (Universal Unique Identifier).
* `travelAgency: UUID` - Ξένο κλειδί προς μία εγγραφή του πίνακα `travel_agency`. Προσδιορίζει το ταξιδιωτικό γραφείο που προσφέρει αυτό το πακέτο.
* `location: UUID` - Ξένο κλειδί προς μία εγγραφή του πίνακα `location`. Προσδιορίζει τον προορισμό στον οποίο αναφέρεται το πακέτο.
* `date: Date` - Η ημερομηνία έναρξης της εκδρομής, τύπου `Date`.
* `price: Double` - Η τιμή του πακέτου, τύπου `Double`.
* `hotels: List<Name>` - Μία λίστα με τα προτεινόμενα ξενοδοχεία του ταξιδιωτικού γραφείου στην περιοχή της εκδρομής, τύπου `List<Name>`, βλεπε(#anchor! Validation).
* `type: LocationType` - Το είδος της εκδρομής, τύπου `LocationType`, βλέπε(#anchor! Validation).

###Registrations (Εγγραφές) - Firestore
```kotlin
data class CustomerDetails(
    val name: Name,
    val surname: Name,
    val phone: Phone,
    val email: Email,
    val hotel: String)

data class Registrations(
    val bundle: UUID,
    val travelAgency: UUID,
    val customers: List<CustomerDetails>)
```
Αναπαριστά το σύνολο των εγγραφών που έχουν γίνει για ένα πακέτο ενός ταξιδιωτικού γραφείου. Κάθε εγγραφή χαρακτηρίζεται από:
* `name: Name` - Το όνομα του πελάτη, τύπου `Name`, βλεπε(ασδαςδασδασδ).
* `surname: Name` - Το επίθετο του πελάτη, τύπου `Name`, βλεπε(ασδασδασδ).
* `phone: Phone` - Το τηλέφωνο επικοινωνίας που δήλωσε ο πελάτης, τύπου `Phone`, βλέπε(ασδασδασδ).
* `email: Email` - Η διεύθυνση ηλεκτρονικόυ ταχυδρομείου επικοινωνίας που δήλωσε ο πελάτης, τύπου `Email`, βλεπε(ασδσαδ).
* `hotel: String` - Το ξενοδοχείο που επέλεξε ο πελάτης (όχι απαραίτητα ένα από τα προτεινόμενα του πακέτου), τύπου `String`.

##Daos και αρχικοποίηση των βάσεων
(package com.steft.travel_app.dao)

###Daos
Για κάθε ένα από τα μοντέλα, δημιουργήθηκε ένα αντίστοιχο Dao με τίς μεθόδους ανάκτησης και τροποποίησης της βάσης που απαιτούνται από την εφαρμογή.

####BundleDao
```kotlin
@Dao
interface BundleDao {
    @Query("SELECT * FROM bundle")
    suspend fun getAll(): List<Bundle>

    @Query("SELECT * FROM bundle WHERE travel_agency = :travelAgency")
    suspend fun findByTravelAgency(travelAgency: UUID): List<Bundle>

    @Query("SELECT * FROM bundle WHERE location = :locationId")
    suspend fun findByLocation(locationId: UUID): List<Bundle>

    @Query("SELECT * FROM bundle WHERE id = :id")
    suspend fun findById(id: UUID): Bundle?

    @Insert
    suspend fun insertAll(vararg bundles: Bundle)

    @Update
    suspend fun update(bundle: Bundle)

    @Query("DELETE FROM bundle WHERE id = :id AND travel_agency = :travelAgency")
    suspend fun delete(id: UUID, travelAgency: UUID): Int
}
```
* `getAll()` - Επιστρέφει όλα τα πακέτα της βάσης.
* `findByTravelAgency(travelAgency: UUID)` - Επιστρέφει όλα τα πακέτα ενός ταξιδιωτικού γραφείου.
* `findByLocation(locationId: UUID)` - Επιστρέφει όλα τα πακέτα που προσφέρονται για έναν προορισμό.
* `findById(id: UUID)` - Επιστρέφει το μοναδικό πακέτο που αντιστοιχεί στο `id`, εφόσων υπάρχει.
* `insertAll(vararg bundles: Bundle)` - Προσθέτει στην βάση όλα τα `bundles`.
* `update(bundle: Bundle)` - Ενημερώνει ένα πακέτο της βάσης με βάση το `bundle`.
* `delete(id: UUID, travelAgency: UUID)` - Διαγράφει το μοναδικό πακέτο που αντιστοιχεί στο `id`, εφόσων ανήκει στο ταξιδιωτικό γραφείο που αντιστοιχεί το `travelAgency`.

####LocationDao
```kotlin
@Dao
interface LocationDao {
    @Query("SELECT * FROM location")
    suspend fun getAll(): List<Location>

    @Query("SELECT * FROM location WHERE travel_agency = :travelAgency OR travel_agency IS NULL")
    suspend fun findAllOfAgency(travelAgency: UUID): List<Location>

    @Query("SELECT * FROM location WHERE id = :id")
    suspend fun findById(id: UUID): Location?

    @Query(
        """
        SELECT * FROM location 
        WHERE city LIKE '%' || :query || '%' 
        OR country LIKE '%' || :query || '%'
        AND travel_agency = :travelAgency
        OR travel_agency IS NULL
        """
    )
    suspend fun searchOfAgency(query: String, travelAgency: UUID): List<Location>

    @Query(
        """
        SELECT * FROM location 
        WHERE city LIKE '%' || :query || '%' 
        OR country LIKE '%' || :query || '%'
        """
    )
    suspend fun searchAll(query: String): List<Location>

    @Insert
    suspend fun insertAll(vararg locations: Location)

    @Query("DELETE FROM location WHERE id = :id AND travel_agency = :travelAgencyId")
    suspend fun deleteCustom(id: UUID, travelAgencyId: UUID): Int


    @Query(
        """
        SELECT * FROM location WHERE id = (
            SELECT location FROM bundle WHERE id = :bundleId
        )
        """)
    suspend fun findFromBundleId(bundleId: UUID): Location?
}
```
* `getAll()` - Επιστρέφει όλους τους προορισμούς της βάσης
* `findAllOfAgency(travelAgency: UUID)` - Επιστρέφει όλους τους προορισμούς στους οποίους έχει πρόσβαση ένας χρήστης ταξιδιωτικού γραφείου (δηλαδή στους προυπάρχοντες και σε αυτούς που δημιούργησε).
* `findById(id: UUID)` - Επιστρέφει τον μοναδικό προορισμό που αντιστοιχεί στο `id`, εφόσων υπάρχει.
* `searchOfAgency(query: String, travelAgency: UUID)` - Επιστρέφει όλους τους προορισμούς με βάση την αναζήτηση της χώρας ή της πόλης `query`, στους οποιους έχει πρόσβαση ο χρήστης ταξιδιωτικού γραφείου που αναγνωρίζεται με `id`.
* `searchAll(query: String)` - Επιστρέφει όλους τους προορισμούς με βάση την αναζήτηση της χώρας ή της πόλης `query`.
* `insertAll(vararg locations: Location)` - Προσθέτει στην βάση όλα τα `locations`
* `deleteCustom(id: UUID, travelAgencyId: UUID)` - Διαγράφει τον προορισμό που αντιστοιχεί στο `id`, εφόσων έχει δημιουργηθεί απο τον χρήστη του ταξιδιωτικόυ γραφείου που αντιστοιχει στο `travelAgencyId`.
* `findFromBundleId(bundleId: UUID)` - Βρίσκει τον προορισμό που έχει δηλωθεί στο πακέτο που αντιστοιχεί στο `bundleid`, εφόσων υπάρχει.

####RegistrationDao
```kotiin
interface RegistrationDao {
    suspend fun getAll(bundle: UUID): List<Registrations>
    suspend fun insert(vararg registrations: Registrations)
    suspend fun register(bundle: UUID, vararg customers: CustomerDetails)
    suspend fun findByAgencyId(travelAgency: UUID): List<Registrations>
    suspend fun registrationsExist(bundle: UUID): Boolean
}

private class RegistrationDaoImpl(private val db: FirebaseFirestore) : RegistrationDao { 
  ... //omitted
}

fun FirebaseFirestore.registrationDao(): RegistrationDao =
    RegistrationDaoImpl(this)
```
Απευθύνεται στην Firestore, οπότε οι μέθοδοι έπρεπε να υλοποιηθούν από εμάς. Βρίσκονται στην private class `RegistrationDaoImpl`. Ενα Dao τυπου `RegistrationDao` μπορεί να ανακτηθεί από την συνάρτηση `FirebaseFirestore.registrationDao()`.
* `getAll(bundle: UUID)` - Επιστρέφει όλες τις εγγραφές που έχουν αναρτηθεί για το bundle που αντιστοιχεί στο `bundleId`.
* `insert(vararg registrations: Registrations)` - Καταχωρεί όλες τις εγγραφές `registrations` στην βάση.
* `register(bundle: UUID, vararg customers: CustomerDetails)` - Αναρτεί ένα σύνολο εγγραφών στο πακέτο που αντιστοιχεί στο `bundle`.
* `findByAgencyId(travelAgency: UUID)` - Επιστρέφει όλες τις εγγραφές που έχουν γίνει σε πακέτα του ταξιδιωτικού γραφείου που αντιστοιχεί στο `travelAgency`.
* `registrationsExist(bundle: UUID)` - Επιστρέφει `true` εφόσων υπάρχουν εγγραφές στο πακέτο που αντιχτοιχεί στο `bundle`. Αλλιώς, επιστρέφει `false`.

####TravelAgencyDao
```kotlin
@Dao
interface TravelAgencyDao {
    @Query("SELECT * FROM travel_agency")
    suspend fun getAll(): List<TravelAgency>

    @Query("SELECT * FROM travel_agency WHERE id = :id")
    suspend fun findById(id: UUID): TravelAgency?

    @Query("SELECT * FROM travel_agency WHERE username = :username")
    suspend fun findByUsername(username: Username): TravelAgency?

    @Insert
    suspend fun insertAll(vararg agencies: TravelAgency)

    @Query("DELETE FROM travel_agency WHERE id = :id")
    suspend fun delete(id: UUID): Int
}
```
* `getAll()` - Επιστρέφει όλα τα ταξιδιωτικά γραφεία της βάσης.
* `findById(id: UUID)` - Επιστρέφει το ταξιδιωτικό γραφείο που αντιστοιχεί στο `id`, εφόσων υπάρχει.
* `findByUsername(username: Username)` - Επιστρέφει το ταξιδιώτικο γραφείο με όνομα χρήστη `username`, εφόσων υπάρχει.
* `insertAll(vararg agencies: TravelAgency)` - Προσθέτει στην βάση όλα τα ταξιδιωτικά γραφεία `agencies`.
* `delete(id: UUID)` - Διαγράφει το ταξιδιωτικό γραφείο που αντιστοιχεί στο `id`

###Αρχικοποίηση βάσεων
Η Sqlite αρχικοποιείται μέσω της συνάρτησης `getDatabase` χρησιμοποιόντας Singleton Pattern ώστε να μη δεσμεύονται πάνω από μία ταυτόχρονες συνδέσεις προς την βάση.
```kotlin
@Database(entities = [TravelAgency::class, Location::class, Bundle::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun travelAgencyDao(): TravelAgencyDao
    abstract fun locationDao(): LocationDao
    abstract fun bundleDao(): BundleDao

    companion object {
        private var instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context, AppDatabase::class.java, "travel_app")
                            .createFromAsset("database/travel_app")
                            .build()
                }
            }
            return instance!!
        }
    }
}
```

Το client library της firestore υλοποιεί εσωτερικά Singleton Pattern, άρα δεν απαιτείται κάποια επιπλέον δράση.
```kotlin
fun firebaseDb() = Firebase.firestore
```











