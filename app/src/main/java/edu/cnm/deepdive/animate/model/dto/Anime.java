package edu.cnm.deepdive.animate.model.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class Anime {

  @Expose(serialize = false, deserialize = true)
  private Images images;

  @Expose(serialize = false, deserialize = true)
  private String title;

  @Expose(serialize = false, deserialize = true)
  private String synopsis;

  @Expose(serialize = false, deserialize = true)
  private String season;

  @Expose(serialize = false, deserialize = true)
  private Integer year;

  @Expose(serialize = false, deserialize = true)
  private Aired aired;

  @Expose(serialize = false, deserialize = true)
  private Type type;


  Anime(String title) {
    this.title = title;
  }


  public Images getImages() {
    return images;
  }

  public void setImages(Images images) {
    this.images = images;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSynopsis() {
    return synopsis;
  }

  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }

  public String getSeason() {
    return season;
  }

  public void setSeason(String season) {
    this.season = season;
  }

  public Integer getYear() {
    return year;
  }

  public void setYear(Integer year) {
    this.year = year;
  }

  public Aired getAired() {
    return aired;
  }

  public void setAired(Aired aired) {
    this.aired = aired;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }


  public static class Images {

    @Expose(serialize = false, deserialize = true)
    private Jpg jpg;

    @Expose(serialize = false, deserialize = true)
    private Webp webp;


    public Jpg getJpg() {
      return jpg;
    }

    public void setJpg(Jpg jpg) {
      this.jpg = jpg;
    }

    public Webp getWebp() {
      return webp;
    }

    public void setWebp(Webp webp) {
      this.webp = webp;
    }


    public static class Jpg {

      @Expose(serialize = false, deserialize = true)
      @SerializedName("image_url")
      private String imageUrl;

      @Expose(serialize = false, deserialize = true)
      @SerializedName("small_image_url")
      private String smallImageUrl;

      @Expose(serialize = false, deserialize = true)
      @SerializedName("large_image_url")
      private String largeImageUrl;


      public String getImageUrl() {
        return imageUrl;
      }

      public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
      }

      public String getSmallImageUrl() {
        return smallImageUrl;
      }

      public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
      }

      public String getLargeImageUrl() {
        return largeImageUrl;
      }

      public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
      }

    }

    public static class Webp {

      @Expose(serialize = false, deserialize = true)
      @SerializedName("image_url")
      private String imageUrl;

      @Expose(serialize = false, deserialize = true)
      @SerializedName("small_image_url")
      private String smallImageUrl;

      @Expose(serialize = false, deserialize = true)
      @SerializedName("large_image_url")
      private String largeImageUrl;


      public String getImageUrl() {
        return imageUrl;
      }

      public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
      }

      public String getSmallImageUrl() {
        return smallImageUrl;
      }

      public void setSmallImageUrl(String smallImageUrl) {
        this.smallImageUrl = smallImageUrl;
      }

      public String getLargeImageUrl() {
        return largeImageUrl;
      }

      public void setLargeImageUrl(String largeImageUrl) {
        this.largeImageUrl = largeImageUrl;
      }

    }

  }


  public static class Aired {

    @Expose(serialize = false, deserialize = true)
    private Instant from;

    @Expose(serialize = false, deserialize = true)
    private Instant to;

    @Expose(serialize = false, deserialize = true)
    private Prop prop;

    @Expose(serialize = false, deserialize = true)
    private String string;


    public Instant getFrom() {
      return from;
    }

    public void setFrom(Instant from) {
      this.from = from;
    }

    public Instant getTo() {
      return to;
    }

    public void setTo(Instant to) {
      this.to = to;
    }

    public Prop getProp() {
      return prop;
    }

    public void setProp(Prop prop) {
      this.prop = prop;
    }

    public String getString() {
      return string;
    }

    public void setString(String string) {
      this.string = string;
    }


    public static class Prop {

      @Expose(serialize = false, deserialize = true)
      private FromTo from;

      @Expose(serialize = false, deserialize = true)
      private FromTo to;


      public FromTo getFrom() {
        return from;
      }

      public void setFrom(FromTo from) {
        this.from = from;
      }

      public FromTo getTo() {
        return to;
      }

      public void setTo(FromTo to) {
        this.to = to;
      }


      public static class FromTo {

        @Expose(serialize = false, deserialize = true)
        private int day;

        @Expose(serialize = false, deserialize = true)
        private int month;

        @Expose(serialize = false, deserialize = true)
        private int year;


        public int getDay() {
          return day;
        }

        public void setDay(int day) {
          this.day = day;
        }

        public int getMonth() {
          return month;
        }

        public void setMonth(int month) {
          this.month = month;
        }

        public int getYear() {
          return year;
        }

        public void setYear(int year) {
          this.year = year;
        }

      }

    }

  }


  public static class InstanceWrapper {

    @Expose(serialize = false, deserialize = true)
    private Anime data;


    public Anime getData() {
      return data;
    }

    public void setData(Anime data) {
      this.data = data;
    }

  }


  public static class ListWrapper {

    @Expose(serialize = false, deserialize = true)
    private Pagination pagination;

    @Expose(serialize = false, deserialize = true)
    private List<Anime> data;


    public Pagination getPagination() {
      return pagination;
    }

    public void setPagination(Pagination pagination) {
      this.pagination = pagination;
    }

    public List<Anime> getData() {
      return data;
    }

    public void setData(List<Anime> data) {
      this.data = data;
    }


    public static class Pagination {

      @Expose(serialize = false, deserialize = true)
      @SerializedName("last_visible_page")
      private int lastVisiblePage;

      @Expose(serialize = false, deserialize = true)
      @SerializedName("has_next_page")
      private boolean hasNextPage;

      @Expose(serialize = false, deserialize = true)
      @SerializedName("current_page")
      private int currentPage;

      @Expose(serialize = false, deserialize = true)
      private Items items;


      public int getLastVisiblePage() {
        return lastVisiblePage;
      }

      public void setLastVisiblePage(int lastVisiblePage) {
        this.lastVisiblePage = lastVisiblePage;
      }

      public boolean isHasNextPage() {
        return hasNextPage;
      }

      public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
      }

      public int getCurrentPage() {
        return currentPage;
      }

      public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
      }

      public Items getItems() {
        return items;
      }

      public void setItems(Items items) {
        this.items = items;
      }


      public static class Items {

        @Expose(serialize = false, deserialize = true)
        private int count;

        @Expose(serialize = false, deserialize = true)
        private int total;

        @Expose(serialize = false, deserialize = true)
        @SerializedName("per_page")
        private int perPage;


        public int getCount() {
          return count;
        }

        public void setCount(int count) {
          this.count = count;
        }

        public int getTotal() {
          return total;
        }

        public void setTotal(int total) {
          this.total = total;
        }

        public int getPerPage() {
          return perPage;
        }

        public void setPerPage(int perPage) {
          this.perPage = perPage;
        }

      }

    }

  }


  public enum Type {

    @SerializedName("tv")
    TV,
    @SerializedName("movie")
    MOVIE,
    @SerializedName("ova")
    OVA,
    @SerializedName("special")
    SPECIAL,
    @SerializedName("ona")
    ONA,
    @SerializedName("music")
    MUSIC,
    @SerializedName("cm")
    CM,
    @SerializedName("pv")
    PV,
    @SerializedName("tv_special")
    TV_SPECIAL;

  }

}
