package edu.cnm.deepdive.animate.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.squareup.picasso.Picasso;
import edu.cnm.deepdive.animate.R;
import edu.cnm.deepdive.animate.databinding.ItemApodBinding;
import edu.cnm.deepdive.animate.model.entity.Apod;
import edu.cnm.deepdive.animate.model.entity.Apod.MediaType;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApodAdapter extends Adapter<ViewHolder> {

  private static final Pattern YOUTUBE_URL =
      Pattern.compile("^(?:https?://)?(?:www\\.)?(?:youtube\\.com/(?:watch\\?v=|embed/|v/)|youtu\\.be/)([a-zA-Z0-9_-]{11})(?:\\S+)?$");
  private static final String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%s/0.jpg";
  private final List<Apod> apods;
  private final OnApodClickListener onThumbnailClickListener;
  private final OnApodClickListener onInfoClickListener;
  private final LayoutInflater inflater;
  private final DateTimeFormatter formatter;

  public ApodAdapter(@NonNull Context context, @NonNull List<Apod> apods,
      @NonNull OnApodClickListener onThumbnailClickListener,
      @NonNull OnApodClickListener onInfoClickListener) {
    this.apods = apods;
    inflater = LayoutInflater.from(context);
    this.onThumbnailClickListener = onThumbnailClickListener;
    this.onInfoClickListener = onInfoClickListener;
    formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
    ItemApodBinding binding = ItemApodBinding.inflate(inflater, viewGroup, false); // false means we don't attach it to that viewGroup, that's the job of the recyclerview, not the adapter's
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    ((Holder) viewHolder).bind(position, apods.get(position));
  }

  @Override
  public int getItemCount() {
    return apods.size();
  }

  private class Holder extends ViewHolder { // to see formatter, we can either remove static (bad), or pass into constructor

    private final ItemApodBinding binding;

    Holder(@NonNull ItemApodBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(int position, Apod apod) {
      binding.title.setText(apod.getTitle().strip());
      binding.date.setText(formatter.format(apod.getDate()));
      binding.mediaTypeThumbnail.setVisibility(View.VISIBLE);
      binding.thumbnail.setContentDescription(apod.getTitle()); // DONE: 6/4/25 Include more info.
      binding.thumbnail.setOnClickListener(
          (v) -> onThumbnailClickListener.onApodClick(apod, position)); // just receives a view object
      binding.info.setOnClickListener((v) -> onInfoClickListener.onApodClick(apod, position));
      MediaType mediaType = apod.getMediaType();
      if (mediaType == MediaType.IMAGE) {
        loadThumbnail(apod.getUrl().toString());
        binding.mediaTypeThumbnail.setImageResource(R.drawable.photo_camera);
      } else if (mediaType == MediaType.VIDEO) {
        Matcher matcher = YOUTUBE_URL.matcher(apod.getUrl().toString());
        if (matcher.matches()) {
          String videoId = matcher.group(1);
          String thumbnailUrl = String.format(YOUTUBE_THUMBNAIL_URL, videoId); // String thumbnailUrl = YOUTUBE_THUMBNAIL_URL.formatted(videoId);
          loadThumbnail(thumbnailUrl);
          binding.mediaTypeThumbnail.setImageResource(R.drawable.video_camera);
        } else {
          binding.thumbnail.setImageResource(R.drawable.video_camera);
          binding.mediaTypeThumbnail.setVisibility(View.GONE);
        }
      } else {
        // DONE: 6/4/25 Load video thumbnail for Youtube video.
        binding.thumbnail.setImageResource(R.drawable.image_not_supported);
        binding.mediaTypeThumbnail.setVisibility(View.GONE);
      }
    }

    private void loadThumbnail(String apod) {
      Picasso.get()
          .load(Uri.parse(apod))
          .into(binding.thumbnail);
    }

  }

  @FunctionalInterface
  public interface OnApodClickListener {

    void onApodClick(Apod apod, int position);

  }

}
