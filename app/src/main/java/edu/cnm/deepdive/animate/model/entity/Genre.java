package edu.cnm.deepdive.animate.model.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.time.Instant;

@Entity(
    tableName = "genre",
    indices = {
        @Index(value = "name", unique = true)
    }
)
public class Genre {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "genre_id")
  private long id;

  @NonNull
  @ColumnInfo(name = "name", collate = ColumnInfo.NOCASE)
  private String name = "";

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

  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
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
