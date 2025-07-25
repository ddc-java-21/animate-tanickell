package edu.cnm.deepdive.animate.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import edu.cnm.deepdive.animate.databinding.FragmentInfoBinding;
import edu.cnm.deepdive.animate.viewmodel.AnimeViewModel;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class InfoFragment extends BottomSheetDialogFragment {

  private FragmentInfoBinding binding;
  private AnimeViewModel viewModel;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    binding = FragmentInfoBinding.inflate(inflater, container, false);
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    viewModel = new ViewModelProvider(requireActivity()).get(AnimeViewModel.class);
    viewModel
        .getAnime()
        .observe(getViewLifecycleOwner(), anime -> {
          DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
          binding.date.setText(formatter.format(
              OffsetDateTime.parse(anime.getAired().getFrom().toString()).toLocalDate())); // anime.getDate().format(formatter) --> date, format yourself (as opposed to formatter, format this date)
          binding.title.setText(anime.getTitle().strip());
          binding.description.setText(anime.getSynopsis().strip());
          if (anime.getSeason() != null && !anime.getSeason().isBlank() && anime.getYear() != null) {
            binding.copyright.setText(anime.getSeason().strip() + " " + anime.getYear());
          } else {
            binding.copyrightLayout.setVisibility(View.GONE);
          }
          // DONE: 6/5/25 Populate view widgets with data from anime.
        });
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

}
