package edu.cnm.deepdive.animate.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;

@Entity(
    tableName = "anime"
)
public class Anime {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "anime_id")
  private long id;

  @Expose(serialize = false, deserialize = true)
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String title;

  @Expose(serialize = false, deserialize = true)
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String genre;

  @Expose(serialize = false, deserialize = true)
  @ColumnInfo(collate = ColumnInfo.NOCASE)
  private String rating;

  @Expose(serialize = false, deserialize = true)
  private double score;

  @Expose(serialize = false, deserialize = true)
  private String description;

  @Expose(serialize = false, deserialize = true)
  private URL posterUrl;

  @Expose(serialize = false, deserialize = true)
  private URL trailerUrl;

  @Expose(serialize = false, deserialize = true)
  @ColumnInfo(name = "release_date")
  private Instant releaseDate;

  @Expose(serialize = false, deserialize = true)
  @NonNull
  @ColumnInfo(name = "date_created")
  private Instant created = Instant.now();

  @Expose(serialize = false, deserialize = true)
  @NonNull
  @ColumnInfo(name = "date_modified")
  private Instant modified = Instant.now();

  @Expose(serialize = false, deserialize = true)
  @SerializedName("media_type")
  @ColumnInfo(name = "media_type")
  private MediaType mediaType;

  @Expose(serialize = false, deserialize = true)
  private String copyright;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getGenre() {
    return genre;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public URL getPosterUrl() {
    return posterUrl;
  }

  public void setPosterUrl(URL posterUrl) {
    this.posterUrl = posterUrl;
  }

  public URL getTrailerUrl() {
    return trailerUrl;
  }

  public void setTrailerUrl(URL trailerUrl) {
    this.trailerUrl = trailerUrl;
  }

  public Instant getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Instant releaseDate) {
    this.releaseDate = releaseDate;
  }

  @NonNull
  public Instant getCreated() {
    return created;
  }

  public void setCreated(@NonNull Instant created) {
    this.created = created;
  }

  @NonNull
  public Instant getModified() {
    return modified;
  }

  public void setModified(@NonNull Instant modified) {
    this.modified = modified;
  }

  public MediaType getMediaType() {
    return mediaType;
  }

  public void setMediaType(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public enum MediaType {

    @SerializedName("image")
    IMAGE,
    @SerializedName("video")
    VIDEO;

  }

}
