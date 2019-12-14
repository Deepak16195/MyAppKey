package it.dikbudsit.stb.myappkey

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import it.dikbudsit.stb.myappkey.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.startScreenFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    fun setTitle(titels :String){
        toolbar.setTitle(titels)
    }


    /*ib navigatuionUP*/
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    /*clear fulscreen flag*/
    fun isVisible(boolean: Boolean) : Boolean{
        window.clearFlags(if (boolean) WindowManager.LayoutParams.FLAG_FULLSCREEN else 0)
        if (boolean)supportActionBar?.show() else supportActionBar?.hide()
        return boolean
    }

}
