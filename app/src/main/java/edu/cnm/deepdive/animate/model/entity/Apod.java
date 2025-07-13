package edu.cnm.deepdive.animate.model.entity;

import androidx.room.Ignore;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.net.URL;
import java.time.LocalDate;

public class Apod {

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
