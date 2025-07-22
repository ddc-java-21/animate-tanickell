package edu.cnm.deepdive.animate.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.time.Instant;

@Entity(
    tableName = "anime_producer",
    foreignKeys = {
        @ForeignKey(
            entity = Anime.class,
            parentColumns = "anime_id",
            childColumns = "anime_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Producer.class,
            parentColumns = "producer_id",
            childColumns = "producer_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class AnimeProducer {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "anime_producer_id")
  private long id;

  @ColumnInfo(name = "anime_id", index = true)
  private long animeId;

  @ColumnInfo(name = "producer_id", index = true)
  private long producerId;

  @NonNull
  @ColumnInfo(name = "date_added", index = true)
  private Instant created = Instant.now();


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getProducerId() {
    return producerId;
  }

  public void setProducerId(long producerId) {
    this.producerId = producerId;
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
