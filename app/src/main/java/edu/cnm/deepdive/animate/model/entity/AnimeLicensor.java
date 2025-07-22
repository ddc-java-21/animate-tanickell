package edu.cnm.deepdive.animate.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.time.Instant;

@Entity(
    tableName = "anime_licensor",
    foreignKeys = {
        @ForeignKey(
            entity = Anime.class,
            parentColumns = "anime_id",
            childColumns = "anime_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Licensor.class,
            parentColumns = "licensor_id",
            childColumns = "licensor_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class AnimeLicensor {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "anime_licensor_id")
  private long id;

  @ColumnInfo(name = "anime_id", index = true)
  private long animeId;

  @ColumnInfo(name = "licensor_id", index = true)
  private long licensorId;

  @NonNull
  @ColumnInfo(name = "date_added", index = true)
  private Instant created = Instant.now();


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getLicensorId() {
    return licensorId;
  }

  public void setLicensorId(long licensorId) {
    this.licensorId = licensorId;
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
