package edu.cnm.deepdive.animate.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.animate.R;
import edu.cnm.deepdive.animate.adapter.AnimeAdapter;
import edu.cnm.deepdive.animate.databinding.FragmentListBinding;
import edu.cnm.deepdive.animate.model.dto.Anime;
import edu.cnm.deepdive.animate.viewmodel.AnimeViewModel;
import edu.cnm.deepdive.animate.viewmodel.LoginViewModel;

@AndroidEntryPoint
public class ListFragment extends Fragment implements MenuProvider {

  private static final String TAG = ListFragment.class.getSimpleName();
  private FragmentListBinding binding;
  private AnimeViewModel viewModel;
  private LoginViewModel loginViewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentListBinding.inflate(inflater, container, false);
    // TODO: 6/4/25 Attach event listeners to view widgets.
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    FragmentActivity activity = requireActivity();
    ViewModelProvider provider = new ViewModelProvider(activity);
    LifecycleOwner owner = getViewLifecycleOwner();

    viewModel = new ViewModelProvider(requireActivity()).get(AnimeViewModel.class);
    viewModel // gives a reference to that inflated layout (recyclerview that was instantiated on inflation)
        .getAnimes()
        .observe(getViewLifecycleOwner(),
            (animes) -> {
              AnimeAdapter adapter = new AnimeAdapter(requireContext(), animes,
                  (anime, pos) -> navigateToMedia(anime), // listener for clicking on a thumbnail
                  (anime, pos) -> navigateToInfo(anime) // listener for clicking on info
              );
              binding.animes.setAdapter(adapter);
            });
    loginViewModel = provider.get(LoginViewModel.class);
    loginViewModel
        .getAccount()
        .observe(owner, (account) -> {
          if (account == null) { // iow, if the user has signed out
            Navigation.findNavController(binding.getRoot())
                .navigate(ListFragmentDirections.showPreLogin());
          }
        });
    viewModel.fetchUser();
    activity.addMenuProvider(this, owner, State.RESUMED);
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

  private void navigateToInfo(Anime anime) {
    viewModel.setAnime(anime);
    Navigation.findNavController(binding.getRoot())
        .navigate(ListFragmentDirections.displayInfo());
  }

  private void navigateToMedia(Anime anime) {
    if (anime.getType() == null) {
      Snackbar.make(
              binding.getRoot(), R.string.no_media_display, Snackbar.LENGTH_LONG)
          .show();
    } else {
      viewModel.setAnime(anime);
      NavController navController = Navigation.findNavController(
          binding.getRoot());
      switch (anime.getType()) {
        case TV -> navController.navigate(ListFragmentDirections.displayImage());
        case MOVIE -> navController.navigate(ListFragmentDirections.displayVideo());
      }
    }
  }

  @Override
  public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
    menuInflater.inflate(R.menu.anime_options, menu); // DONE: 6/16/25 Inflate a menu resource, attaching the inflated items to the specified menu.
  }

  @Override
  public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
    // DONE: 6/16/25 Check the ID of menuItem, to see if it is of interest to us; if so, perform
    //  appropriate operations and return true; otherwise, return false.
    boolean handled = false;
    if (menuItem.getItemId() == R.id.sign_out) {
      loginViewModel.signOut();
      handled = true;
    }
    return handled;
  }
  
}
