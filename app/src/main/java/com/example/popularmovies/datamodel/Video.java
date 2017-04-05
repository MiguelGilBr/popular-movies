package com.example.popularmovies.datamodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.NotNull;

@Entity
public class Video {
    @SerializedName("id")
    @Expose
    @Id
    private long id;
    @SerializedName("iso_639_1")
    @Expose
    private String iso6391;
    @SerializedName("iso_3166_1")
    @Expose
    private String iso31661;
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("site")
    @Expose
    private String site;
    @SerializedName("size")
    @Expose
    private Integer size;
    @SerializedName("type")
    @Expose
    private String type;

    private long movieId;
    @ToOne(joinProperty = "movieId")
    private Movie movie;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 2004496110)
    private transient VideoDao myDao;

    @Generated(hash = 1770469773)
    public Video(long id, String iso6391, String iso31661, String key, String name,
            String site, Integer size, String type, long movieId) {
        this.id = id;
        this.iso6391 = iso6391;
        this.iso31661 = iso31661;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.type = type;
        this.movieId = movieId;
    }
    @Generated(hash = 237528154)
    public Video() {
    }
    @Generated(hash = 708760245)
    private transient Long movie__resolvedKey;

    //GETTERS & SETTERs
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getIso6391() {
        return iso6391;
    }
    public void setIso6391(String iso6391) {
        this.iso6391 = iso6391;
    }
    public String getIso31661() {
        return iso31661;
    }
    public void setIso31661(String iso31661) {
        this.iso31661 = iso31661;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
    public long getMovieId() {
        return this.movieId;
    }
    public void setMovieId(long movieId) {
        this.movieId = movieId;
    }
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 1088418243)
    public Movie getMovie() {
        long __key = this.movieId;
        if (movie__resolvedKey == null || !movie__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MovieDao targetDao = daoSession.getMovieDao();
            Movie movieNew = targetDao.load(__key);
            synchronized (this) {
                movie = movieNew;
                movie__resolvedKey = __key;
            }
        }
        return movie;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 2024058190)
    public void setMovie(@NotNull Movie movie) {
        if (movie == null) {
            throw new DaoException(
                    "To-one property 'movieId' has not-null constraint; cannot set to-one to null");
        }
        synchronized (this) {
            this.movie = movie;
            movieId = movie.getId();
            movie__resolvedKey = movieId;
        }
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 658121286)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getVideoDao() : null;
    }
}

