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
import edu.cnm.deepdive.animate.databinding.ItemAnimeBinding;
//import edu.cnm.deepdive.animate.model.entity.Anime.MediaType;
import edu.cnm.deepdive.animate.model.dto.Anime;
import edu.cnm.deepdive.animate.model.dto.Anime.Type;
import edu.cnm.deepdive.animate.model.entity.Apod.MediaType;
import edu.cnm.deepdive.animate.model.entity.Apod;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AnimeAdapter extends Adapter<ViewHolder> {

  private static final Pattern YOUTUBE_URL =
      Pattern.compile("^(?:https?://)?(?:www\\.)?(?:youtube\\.com/(?:watch\\?v=|embed/|v/)|youtu\\.be/)([a-zA-Z0-9_-]{11})(?:\\S+)?$");
  private static final String YOUTUBE_THUMBNAIL_URL = "https://img.youtube.com/vi/%s/0.jpg";
  private final List<Anime> animes;
  private final OnAnimeClickListener onThumbnailClickListener;
  private final OnAnimeClickListener onInfoClickListener;
  private final LayoutInflater inflater;
  private final DateTimeFormatter formatter;

  public AnimeAdapter(@NonNull Context context, @NonNull List<Anime> animes,
      @NonNull OnAnimeClickListener onThumbnailClickListener,
      @NonNull OnAnimeClickListener onInfoClickListener) {
    this.animes = animes;
    inflater = LayoutInflater.from(context);
    this.onThumbnailClickListener = onThumbnailClickListener;
    this.onInfoClickListener = onInfoClickListener;
    formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int itemType) {
    ItemAnimeBinding binding = ItemAnimeBinding.inflate(inflater, viewGroup, false); // false means we don't attach it to that viewGroup, that's the job of the recyclerview, not the adapter's
    return new Holder(binding);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
    ((Holder) viewHolder).bind(position, animes.get(position));
  }

  @Override
  public int getItemCount() {
    return animes.size();
  }

  private class Holder extends ViewHolder { // to see formatter, we can either remove static (bad), or pass into constructor

    private final ItemAnimeBinding binding;

    Holder(@NonNull ItemAnimeBinding binding) {
      super(binding.getRoot());
      this.binding = binding;
    }

    void bind(int position, Anime anime) {
      binding.title.setText(anime.getTitle().strip());
      binding.date.setText(formatter.format(
          OffsetDateTime.parse(anime.getAired().getFrom().toString()).toLocalDate()));
      binding.mediaTypeThumbnail.setVisibility(View.VISIBLE);
      binding.thumbnail.setContentDescription(anime.getTitle()); // DONE: 6/4/25 Include more info.
      binding.thumbnail.setOnClickListener(
          (v) -> onThumbnailClickListener.onAnimeClick(anime, position)); // just receives a view object
      binding.info.setOnClickListener((v) -> onInfoClickListener.onAnimeClick(anime, position));
      Type type = anime.getType();
      if (type == Type.TV) {
        loadThumbnail(anime.getImages().getJpg().getImageUrl().toString());
        binding.mediaTypeThumbnail.setImageResource(R.drawable.photo_camera);
      } else if (type == Type.MOVIE) {
        Matcher matcher = YOUTUBE_URL.matcher(anime.getImages().getJpg().getImageUrl().toString());
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

    private void loadThumbnail(String anime) {
      Picasso.get()
          .load(Uri.parse(anime))
          .into(binding.thumbnail);
    }

  }

  @FunctionalInterface
  public interface OnAnimeClickListener {

    void onAnimeClick(Anime anime, int position);

  }

}
