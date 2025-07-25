package edu.cnm.deepdive.animate.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import edu.cnm.deepdive.animate.R;
import edu.cnm.deepdive.animate.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private ActivityMainBinding binding;
  private AppBarConfiguration appBarConfiguration;
  private NavController navController;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupUI();
    setupNavigation();
  }

  @Override
  public boolean onSupportNavigateUp() {
    return NavigationUI.navigateUp(navController, appBarConfiguration);
  }

  private void setupNavigation() {
    appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.pre_login_fragment, R.id.login_fragment, R.id.list_fragment)
        .build();
    navController =
        ((NavHostFragment) binding.navHostFragmentContainer.getFragment()).getNavController();     // reference to the piece of machinery that will swap in one fragment for another
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
  }

  private void setupUI() {
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot()); // use binding.getRoot() instead of R.layout.activity_main
  }

}