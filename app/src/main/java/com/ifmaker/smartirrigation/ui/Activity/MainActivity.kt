package com.ifmaker.smartirrigation.ui.Activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ifmaker.smartirrigation.ui.Fragment.ClimaFragment
import com.ifmaker.smartirrigation.ui.Fragment.ConfigFragment
import com.ifmaker.smartirrigation.ui.Fragment.HistoricoFragment
import com.ifmaker.smartirrigation.ui.Fragment.IrrigacaoFragment
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ifmaker.smartirrigation.R

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.top_nav_bar)
        bottomNav = findViewById(R.id.bottom_navigation)
        setSupportActionBar(toolbar)

        toolbar.menu.clear()
        toolbar.inflateMenu(R.menu.top_bar)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_profile -> {
                    bottomNav.selectedItemId = R.id.nav_config
                    true
                }
                else -> false
            }
        }

        bottomNav.setOnItemSelectedListener { item ->
            onClickBottomNav(item)
        }

        // 4) Estado inicial
        if (savedInstanceState == null) {
            // define fragment inicial sem adicionar ao back stack
            loadFragment(ClimaFragment())
            bottomNav.selectedItemId = R.id.nav_clima
            setToolbarTitle("Clima")
        }

    }

    // Chamada do BottomNavigationView
    private fun onClickBottomNav(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_historico -> {
                loadFragment(HistoricoFragment())
                setToolbarTitle("Histórico")
            }

            R.id.nav_irrigacao -> {
                loadFragment(IrrigacaoFragment())
                setToolbarTitle("Irrigação")
            }

            R.id.nav_clima -> {
                loadFragment(ClimaFragment())
                setToolbarTitle("Clima")
            }

            R.id.nav_config -> {
                loadFragment(ConfigFragment())
                setToolbarTitle("Configurações")
            }

            else -> return false
        }
        return true
    }


    // Método público que fragments podem chamar para mudar título / mostrar seta
    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    // Carrega fragment (passar addToBackStack=true para telas internas)
    fun loadFragment(fragment: Fragment) {
        val tx = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
        tx.commit()

        invalidateOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_bar, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val current = supportFragmentManager.findFragmentById(R.id.fragment_container)

        val profileItem = menu.findItem(R.id.action_profile)
        profileItem.isVisible = current !is ConfigFragment
        profileItem.isEnabled = current !is ConfigFragment

        return super.onPrepareOptionsMenu(menu)
    }

}
