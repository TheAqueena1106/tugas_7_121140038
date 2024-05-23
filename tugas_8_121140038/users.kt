class users {
    import androidx.room.Entity
    import androidx.room.PrimaryKey

    @Entity(tableName = "users")
    data class User(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0,
        val username: String,
        val email: String
    )
    import androidx.room.Dao
    import androidx.room.Insert
    import androidx.room.Query

    @Dao
    interface UserDao {
        @Query("SELECT * FROM users")
        fun getAllUsers(): List<User>

        @Insert
        fun insertUser(user: User)
    }
    import androidx.room.Database
    import androidx.room.RoomDatabase

    @Database(entities = [User::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun userDao(): UserDao
    }
    import android.app.Application
    import androidx.room.Room

    class MyApp : Application() {
        companion object {
            lateinit var database: AppDatabase
        }

        override fun onCreate() {
            super.onCreate()
            database = Room.databaseBuilder(
                this, AppDatabase::class.java, "my_database")
                .build()

            // Isi data dummy ke dalam database
            Thread {
                val userDao = database.userDao()
                userDao.insertUser(User(
                    username = "John Doe", email = "john@example.com"))
                userDao.insertUser(User(
                    username = "Jane Smith", email = "jane@example.com"))
                userDao.insertUser(User(
                    username = "Mike Johnson", email = "mike@example.com"))
            }.start()
        }
    }
    import android.os.Bundle
    import androidx.appcompat.app.AppCompatActivity
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView

    class MainActivity : AppCompatActivity() {
        private lateinit var recyclerView: RecyclerView
        private lateinit var adapter: UserAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_main)

            recyclerView = findViewById(R.id.recyclerView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            adapter = UserAdapter(emptyList())
            recyclerView.adapter = adapter

            // Mendapatkan data pengguna dari database Room
            Thread {
                val userDao = MyApp.database.userDao()
                val users = userDao.getAllUsers()
                runOnUiThread {
                    adapter.setData(users)
                }
            }.start()
        }
    }
}