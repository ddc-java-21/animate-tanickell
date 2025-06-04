package edu.cnm.deepdive.apod.controller;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.apod.databinding.FragmentImageBinding;
import edu.cnm.deepdive.apod.viewmodel.ApodViewModel;
import java.time.LocalDate;

public class ImageFragment extends Fragment {

  private FragmentImageBinding binding;
  private ApodViewModel viewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentImageBinding.inflate(inflater, container, false); // job of the nav component to immediately add this to its container
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    // DONE: 6/4/25 Create a viewmodel provider, and use it to get a reference to viewmodel instance.
    viewModel = new ViewModelProvider(requireActivity()).get(ApodViewModel.class);
    viewModel
        .getApod()
        .observe(getViewLifecycleOwner(), (apod) -> Picasso.get()
            .load(Uri.parse(apod.getUrl().toString()))
            .into(binding.image));
    viewModel.fetch(ImageFragmentArgs.fromBundle(getArguments()).getDate()); // bundle is a map (string keys to objects)  // generated from nav_graph <argument> element

  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
}
