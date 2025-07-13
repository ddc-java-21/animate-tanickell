package edu.cnm.deepdive.animate.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.animate.R;
import edu.cnm.deepdive.animate.adapter.AnimeAdapter;
import edu.cnm.deepdive.animate.databinding.FragmentListBinding;
import edu.cnm.deepdive.animate.model.entity.Anime;
import edu.cnm.deepdive.animate.model.entity.Apod;
import edu.cnm.deepdive.animate.viewmodel.AnimeViewModel;

public class ListFragment extends Fragment {

  private static final String TAG = ListFragment.class.getSimpleName();
  private FragmentListBinding binding;
  private AnimeViewModel viewModel;

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
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

  private void navigateToInfo(Apod anime) {
    viewModel.setAnime(anime);
    Navigation.findNavController(binding.getRoot())
        .navigate(ListFragmentDirections.displayInfo());
  }

  private void navigateToMedia(Apod anime) {
    if (anime.getMediaType() == null) {
      Snackbar.make(
              binding.getRoot(), R.string.no_media_display, Snackbar.LENGTH_LONG)
          .show();
    } else {
      viewModel.setAnime(anime);
      NavController navController = Navigation.findNavController(
          binding.getRoot());
      switch (anime.getMediaType()) {
        case IMAGE -> navController.navigate(ListFragmentDirections.displayImage());
        case VIDEO -> navController.navigate(ListFragmentDirections.displayVideo());
      }
    }
  }
  
}
