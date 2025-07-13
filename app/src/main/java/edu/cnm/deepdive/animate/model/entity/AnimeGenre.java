package edu.cnm.deepdive.animate.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.time.Instant;

@Entity(
    tableName = "anime_genre",
    foreignKeys = {
        @ForeignKey(
            entity = Genre.class,
            parentColumns = "genre_id",
            childColumns = "genre_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Anime.class,
            parentColumns = "anime_id",
            childColumns = "anime_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class AnimeGenre {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "anime_genre_id")
  private long id;

  @ColumnInfo(name = "genre_id", index = true)
  private long genreId;

  @ColumnInfo(name = "anime_id", index = true)
  private long animeId;

  @NonNull
  @ColumnInfo(name = "date_added", index = true)
  private Instant created = Instant.now();


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getGenreId() {
    return genreId;
  }

  public void setGenreId(long genreId) {
    this.genreId = genreId;
  }

  public long getAnimeId() {
    return animeId;
  }

  public void setAnimeId(long animeId) {
    this.animeId = animeId;
  }

  @NonNull
  public Instant getCreated() {
    return created;
  }

  public void setCreated(@NonNull Instant created) {
    this.created = created;
  }

}
