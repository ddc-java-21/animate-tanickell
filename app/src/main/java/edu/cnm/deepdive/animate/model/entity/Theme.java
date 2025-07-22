package edu.cnm.deepdive.animate.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.net.URL;
import java.time.Instant;

@Entity(
    tableName = "theme",
    indices = {
        @Index(value = "name", unique = true)
    }
)
public class Theme {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "theme_id")
  private long id;

  @ColumnInfo(name = "mal_id")
  private long malId;

  @ColumnInfo(name = "type", collate = ColumnInfo.NOCASE)
  private String type;

  @ColumnInfo(name = "name", collate = ColumnInfo.NOCASE)
  private String name = "";

  @ColumnInfo(name = "url", collate = ColumnInfo.NOCASE)
  private URL url;

  @NonNull
  @ColumnInfo(name = "date_created", index = true)
  private Instant created = Instant.now();

  @NonNull
  @ColumnInfo(name = "date_modified", index = true)
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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public URL getUrl() {
    return url;
  }

  public void setUrl(URL url) {
    this.url = url;
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

}
