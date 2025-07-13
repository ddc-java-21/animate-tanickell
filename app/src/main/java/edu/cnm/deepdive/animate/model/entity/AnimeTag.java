package edu.cnm.deepdive.animate.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import java.time.Instant;

@Entity(
    tableName = "anime_tag",
    foreignKeys = {
        @ForeignKey(
            entity = User.class,
            parentColumns = "user_id",
            childColumns = "user_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Anime.class,
            parentColumns = "anime_id",
            childColumns = "anime_id",
            onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
            entity = Tag.class,
            parentColumns = "tag_id",
            childColumns = "tag_id",
            onDelete = ForeignKey.CASCADE
        )
    }
)
public class AnimeTag {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "anime_tag_id")
  private long id;

  @ColumnInfo(name = "user_id", index = true)
  private Long userId;

  @ColumnInfo(name = "anime_id", index = true)
  private long animeId;

  @ColumnInfo(name = "tag_id", index = true)
  private long tagId;

  @NonNull
  @ColumnInfo(name = "date_tagged", index = true)
  private Instant created = Instant.now();


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getAnimeId() {
    return animeId;
  }

  public void setAnimeId(long animeId) {
    this.animeId = animeId;
  }

  public long getTagId() {
    return tagId;
  }

  public void setTagId(long tagId) {
    this.tagId = tagId;
  }

  @NonNull
  public Instant getCreated() {
    return created;
  }

  public void setCreated(@NonNull Instant created) {
    this.created = created;
  }

}
