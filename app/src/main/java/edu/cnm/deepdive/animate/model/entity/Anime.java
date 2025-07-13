package edu.cnm.deepdive.animate.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;

@Entity(
    tableName = "anime",
    indices = {
        @Index(value = "malId", unique = true)
    }
)
public class Anime {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "anime_id")
  private long id;

  @ColumnInfo(name = "mal_id")
  private long malId;

  @Expose(serialize = false, deserialize = true)
  private URL malUrl;

  @Expose(serialize = false, deserialize = true)
  private URL posterUrl;

  @Expose(serialize = false, deserialize = true)
  private URL trailerUrl;

  @Expose(serialize = false, deserialize = true)
  @ColumnInfo(name = "title", collate = ColumnInfo.NOCASE)
  private String animeTitle;

  @Expose(serialize = false, deserialize = true)
  @ColumnInfo(name = "title", collate = ColumnInfo.NOCASE)
  private String titleEnglish;

  @Expose(serialize = false, deserialize = true)
  @ColumnInfo(name = "title", collate = ColumnInfo.NOCASE)
  private String titleJapanese;

  @Expose(serialize = false, deserialize = true)
  private String type;

  @Expose(serialize = false, deserialize = true)
  private String source;

  @Expose(serialize = false, deserialize = true)
  private Integer episodes;

  @Expose(serialize = false, deserialize = true)
  private String status;

  @Expose(serialize = false, deserialize = true)
  private Boolean airing;

  @Expose(serialize = false, deserialize = true)
  @ColumnInfo(name = "date_aired")
  private Instant airedFrom;

  @Expose(serialize = false, deserialize = true)
  @ColumnInfo(name = "date_finished")
  private Instant airedTo;

  @Expose(serialize = false, deserialize = true)
  private String airedString;

  @Expose(serialize = false, deserialize = true)
  private String duration;

  @Expose(serialize = false, deserialize = true)
  private String rating;

  @Expose(serialize = false, deserialize = true)
  private Double score;

  @Expose(serialize = false, deserialize = true)
  private Integer rank;

  @Expose(serialize = false, deserialize = true)
  private Integer popularity;

  @Expose(serialize = false, deserialize = true)
  private String synopsis;

  @Expose(serialize = false, deserialize = true)
  private String background;

  @Expose(serialize = false, deserialize = true)
  private String season;

  @Expose(serialize = false, deserialize = true)
  private int year;

  @Expose(serialize = false, deserialize = true)
  private String broadcast;

  @Expose(serialize = false, deserialize = true)
  @NonNull
  @ColumnInfo(name = "date_created")
  private Instant created = Instant.now();

  @Expose(serialize = false, deserialize = true)
  @NonNull
  @ColumnInfo(name = "date_modified")
  private Instant modified = Instant.now();


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getMalId() {
    return malId;
  }

  public void setMalId(long malId) {
    this.malId = malId;
  }

  public URL getMalUrl() {
    return malUrl;
  }

  public void setMalUrl(URL malUrl) {
    this.malUrl = malUrl;
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

  public String getAnimeTitle() {
    return animeTitle;
  }

  public void setAnimeTitle(String animeTitle) {
    this.animeTitle = animeTitle;
  }

  public String getTitleEnglish() {
    return titleEnglish;
  }

  public void setTitleEnglish(String titleEnglish) {
    this.titleEnglish = titleEnglish;
  }

  public String getTitleJapanese() {
    return titleJapanese;
  }

  public void setTitleJapanese(String titleJapanese) {
    this.titleJapanese = titleJapanese;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Integer getEpisodes() {
    return episodes;
  }

  public void setEpisodes(Integer episodes) {
    this.episodes = episodes;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Boolean getAiring() {
    return airing;
  }

  public void setAiring(Boolean airing) {
    this.airing = airing;
  }

  public Instant getAiredFrom() {
    return airedFrom;
  }

  public void setAiredFrom(Instant airedFrom) {
    this.airedFrom = airedFrom;
  }

  public Instant getAiredTo() {
    return airedTo;
  }

  public void setAiredTo(Instant airedTo) {
    this.airedTo = airedTo;
  }

  public String getAiredString() {
    return airedString;
  }

  public void setAiredString(String airedString) {
    this.airedString = airedString;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getRating() {
    return rating;
  }

  public void setRating(String rating) {
    this.rating = rating;
  }

  public Double getScore() {
    return score;
  }

  public void setScore(Double score) {
    this.score = score;
  }

  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  public Integer getPopularity() {
    return popularity;
  }

  public void setPopularity(Integer popularity) {
    this.popularity = popularity;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

  public String getBackground() {
    return background;
  }

  public void setBackground(String background) {
    this.background = background;
  }

  public String getSeason() {
    return season;
  }

  public void setSeason(String season) {
    this.season = season;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getBroadcast() {
    return broadcast;
  }

  public void setBroadcast(String broadcast) {
    this.broadcast = broadcast;
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


  @Ignore
  @Expose(serialize = false, deserialize = true)
  private LocalDate date;

  @Ignore
  @Expose(serialize = false, deserialize = true)
  private String title;

  @Ignore
  @Expose(serialize = false, deserialize = true)
  private String explanation;

  @Ignore
  @Expose(serialize = false, deserialize = true)
  private String copyright;

  @Ignore
  @Expose(serialize = false, deserialize = true)
  private URL url;

  @Ignore
  @Expose(serialize = false, deserialize = true)
  private URL hdurl;

  @Ignore
  @Expose(serialize = false, deserialize = true)
  @SerializedName("media_type")
  private MediaType mediaType;


  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }

  public void setUrl(URL url) {
    this.url = url;
  }

  public void setHdurl(URL hdurl) {
    this.hdurl = hdurl;
  }

  public void setMediaType(MediaType mediaType) {
    this.mediaType = mediaType;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setExplanation(String explanation) {
    this.explanation = explanation;
  }

  public LocalDate getDate() {
    return date;
  }

  public String getTitle() {
    return title;
  }

  public String getExplanation() {
    return explanation;
  }

  public String getCopyright() {
    return copyright;
  }

  public URL getUrl() {
    return url;
  }

  public URL getHdurl() {
    return hdurl;
  }

  public MediaType getMediaType() {
    return mediaType;
  }

  public enum MediaType {

    @SerializedName("image")
    IMAGE,
    @SerializedName("video")
    VIDEO;

  }

}
