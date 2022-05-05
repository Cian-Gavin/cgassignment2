package cg.p.cgassignment2

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import cg.p.cgassignment2.databinding.ActivityMainBinding
import cg.p.cgassignment2.databinding.NavHeaderMainBinding
import cg.p.cgassignment2.ui.Authentication.LoggedInViewModel
import cg.p.cgassignment2.ui.Authentication.Login
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var WelcomeBinding : ActivityMainBinding
    private lateinit var navHeaderBinding : NavHeaderMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var loggedInViewModel : LoggedInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

       WelcomeBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(WelcomeBinding.root)
        drawerLayout = WelcomeBinding.drawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
//        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.
        findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        val navController = navHostFragment.navController

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_welcome, R.id.add_group, R.id.list_group), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val navView = WelcomeBinding.navView
        navView.setupWithNavController(navController)
    }

    public override fun onStart() {
        super.onStart()
        loggedInViewModel = ViewModelProvider(this).get(LoggedInViewModel::class.java)
        loggedInViewModel.liveFirebaseUser.observe(this, Observer { firebaseUser ->
            if (firebaseUser != null)
                updateNavHeader(loggedInViewModel.liveFirebaseUser.value!!)
        })

        loggedInViewModel.loggedOut.observe(this, Observer { loggedout ->
            if (loggedout) {
                startActivity(Intent(this, Login::class.java))
            }
        })

    }

    private fun updateNavHeader(currentUser: FirebaseUser) {
        var headerView = WelcomeBinding.navView.getHeaderView(0)
        navHeaderBinding = NavHeaderMainBinding.bind(headerView)
        navHeaderBinding.textView.text = currentUser.email
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun signOut(item: MenuItem) {
        loggedInViewModel.logOut()
        //Launch Login activity and clear the back stack to stop navigating back to the Home activity
        val intent = Intent(this, Login::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}
