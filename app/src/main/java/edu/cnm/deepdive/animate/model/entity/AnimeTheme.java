package edu.cnm.deepdive.animate.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.time.Instant;

@Entity(
    tableName = "anime_theme",
    foreignKeys = {
        @ForeignKey(
            entity = Anime.class,
            parentColumns = "anime_id",
            childColumns = "anime_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Theme.class,
            parentColumns = "theme_id",
            childColumns = "theme_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class AnimeTheme {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "anime_theme_id")
  private long id;

  @ColumnInfo(name = "anime_id", index = true)
  private long animeId;

  @ColumnInfo(name = "theme_id", index = true)
  private long themeId;

  @NonNull
  @ColumnInfo(name = "date_added", index = true)
  private Instant created = Instant.now();


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getThemeId() {
    return themeId;
  }

  public void setThemeId(long themeId) {
    this.themeId = themeId;
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
