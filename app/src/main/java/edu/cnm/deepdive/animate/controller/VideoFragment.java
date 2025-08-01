package edu.cnm.deepdive.animate.controller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle.State;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.animate.R;
import edu.cnm.deepdive.animate.databinding.FragmentVideoBinding;
import edu.cnm.deepdive.animate.model.dto.Anime;
import edu.cnm.deepdive.animate.model.entity.Apod;
import edu.cnm.deepdive.animate.viewmodel.AnimeViewModel;

public class VideoFragment extends Fragment implements MenuProvider {

  private FragmentVideoBinding binding;

  @SuppressLint("SetJavaScriptEnabled")
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentVideoBinding.inflate(inflater, container, false);
    binding.content.setWebViewClient(new Client());
    WebSettings settings = binding.content.getSettings();
    settings.setJavaScriptEnabled(true);
    settings.setSupportZoom(true);
    settings.setBuiltInZoomControls(true);
    settings.setDisplayZoomControls(false);
    settings.setUseWideViewPort(true);
    settings.setLoadWithOverviewMode(true);
    requireActivity().addMenuProvider(this, getViewLifecycleOwner(), State.RESUMED);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    AnimeViewModel viewModel = new ViewModelProvider(requireActivity()).get(AnimeViewModel.class);
    viewModel
        .getAnime()
        .observe(getViewLifecycleOwner(), this::displayAnime);
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

  @Override
  public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
  menuInflater.inflate(R.menu.video_actions, menu);
  }

  @Override
  public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
    boolean handled = false;
    if (menuItem.getItemId() == R.id.show_info) {
      handled = true; // otherwise it'll keep asking the other menu providers
      Navigation.findNavController(binding.getRoot())
          .navigate(VideoFragmentDirections.displayInfo());
    }
    return handled;
  }

  private void displayAnime(Anime anime) {
    //noinspection DataFlowIssue
    ((AppCompatActivity) requireActivity())
        .getSupportActionBar() // we know we have an action bar, so even though it's nullable, we'll be ok
        .setTitle(anime.getTitle());
    binding.content.loadUrl(anime.getImages().getJpg().getImageUrl());
  }

  private class Client extends WebViewClient {

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
      return false; // if not false, it will turn over control to a web browser
    }

    @Override
    public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      // TODO: 6/4/25 Cleanup UI (e.g., loading indicator).
      binding.loadingIndicator.setVisibility(View.GONE);
    }
  }

}
