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
import edu.cnm.deepdive.animate.databinding.ItemAnimateBinding;
import edu.cnm.deepdive.animate.model.Animate;
import edu.cnm.deepdive.animate.model.Animate.MediaType;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimateAdapter extends Adapter<ViewHolder> {

  private static final Pattern YOUTUBE_URL =
      Pattern.compile("^(?:https?://)?(?:www\\.)?(?:youtube\\.com/(?:watch\\?v=|embed/|v/)|youtu\\.be/)([a-zA-Z0-9_-]{11})(?:\\S+)?$");
  private static final String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%s/0.jpg";
  private final List<Animate> animates;
  private final OnAnimateClickListener onThumbnailClickListener;
  private final OnAnimateClickListener onInfoClickListener;
  private final LayoutInflater inflater;
  private final DateTimeFormatter formatter;

  public AnimateAdapter(@NonNull Context context, @NonNull List<Animate> animates,
      @NonNull OnAnimateClickListener onThumbnailClickListener,
      @NonNull OnAnimateClickListener onInfoClickListener) {
    this.animates = animates;
    inflater = LayoutInflater.from(context);
    this.onThumbnailClickListener = onThumbnailClickListener;
    this.onInfoClickListener = onInfoClickListener;
    formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
    ItemAnimateBinding binding = ItemAnimateBinding.inflate(inflater, viewGroup, false); // false means we don't attach it to that viewGroup, that's the job of the recyclerview, not the adapter's
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    ((Holder) viewHolder).bind(position, animates.get(position));
  }

  @Override
  public int getItemCount() {
    return animates.size();
  }

  private class Holder extends ViewHolder { // to see formatter, we can either remove static (bad), or pass into constructor

    private final ItemAnimateBinding binding;

    Holder(@NonNull ItemAnimateBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(int position, Animate animate) {
      binding.title.setText(animate.getTitle().strip());
      binding.date.setText(formatter.format(animate.getDate()));
      binding.mediaTypeThumbnail.setVisibility(View.VISIBLE);
      binding.thumbnail.setContentDescription(animate.getTitle()); // DONE: 6/4/25 Include more info.
      binding.thumbnail.setOnClickListener(
          (v) -> onThumbnailClickListener.onAnimateClick(animate, position)); // just receives a view object
      binding.info.setOnClickListener((v) -> onInfoClickListener.onAnimateClick(animate, position));
      MediaType mediaType = animate.getMediaType();
      if (mediaType == MediaType.IMAGE) {
        loadThumbnail(animate.getUrl().toString());
        binding.mediaTypeThumbnail.setImageResource(R.drawable.photo_camera);
      } else if (mediaType == MediaType.VIDEO) {
        Matcher matcher = YOUTUBE_URL.matcher(animate.getUrl().toString());
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

    private void loadThumbnail(String animate) {
      Picasso.get()
          .load(Uri.parse(animate))
          .into(binding.thumbnail);
    }

  }

  @FunctionalInterface
  public interface OnAnimateClickListener {

    void onAnimateClick(Animate animate, int position);

  }

}
