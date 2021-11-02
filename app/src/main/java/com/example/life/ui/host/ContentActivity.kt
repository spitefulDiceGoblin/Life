package com.example.life.ui.host

import android.content.Context
import android.content.Intent
import android.os.Bundle
// import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.*
import com.example.life.R
import com.example.life.databinding.ActivityContentBinding

class ContentActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityContentBinding

    // Source https://medium.com/@anoopg87/set-start-destination-for-navhostfragment-dynamically-b072a29bfe49
    companion object {
        private const val IS_DEST_GAME = "isDestinationGame"
        fun open(context: Context, isDestinationGame: Boolean) {
            context.startActivity(Intent(context, ContentActivity::class.java).apply {
                putExtra(IS_DEST_GAME, isDestinationGame)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarContent.toolbar)


        // triggers FAB - not using ATM
        // binding.appBarContent.fab.setOnClickListener { view ->
        //    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        //            .setAction("Action", null).show()
        //}

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment)
        val inflater = navController.navInflater
        val graph = inflater.inflate(R.navigation.mobile_navigation)
        val destination = if (intent.getBooleanExtra(IS_DEST_GAME, true))
            R.id.nav_game_run else R.id.nav_wiki
        graph.startDestination = destination
        navController.graph = graph

        // You must call setGraph() before calling getGraph()
        // navController.graph.startDestination = destination

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_game_edit, R.id.nav_wiki
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    // Only in Game fragment
    //override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.content, menu)
        //return super.onCreateOptionsMenu(menu)
    //}

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}