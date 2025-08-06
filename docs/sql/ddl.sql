-- Generated 2025-08-06 14:37:54-0600 for database version 1

CREATE TABLE IF NOT EXISTS `user`
(
    `user_id`       INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `oauth_key`     TEXT                              NOT NULL,
    `display_name`  TEXT                              NOT NULL COLLATE NOCASE,
    `date_created`  INTEGER                           NOT NULL,
    `date_modified` INTEGER                           NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_user_oauth_key` ON `user` (`oauth_key`);

CREATE UNIQUE INDEX IF NOT EXISTS `index_user_display_name` ON `user` (`display_name`);

CREATE TABLE IF NOT EXISTS `anime`
(
    `anime_id`       INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `mal_id`         INTEGER,
    `mal_url`        TEXT,
    `poster_url`     TEXT,
    `trailer_url`    TEXT,
    `title`          TEXT COLLATE NOCASE,
    `title_english`  TEXT COLLATE NOCASE,
    `title_japanese` TEXT COLLATE NOCASE,
    `type`           TEXT,
    `source`         TEXT,
    `episodes`       INTEGER,
    `status`         TEXT,
    `airing`         INTEGER,
    `date_released`  INTEGER,
    `date_finished`  INTEGER,
    `aired_summary`  TEXT,
    `duration`       TEXT,
    `rating`         TEXT,
    `score`          REAL,
    `rank`           INTEGER,
    `popularity`     INTEGER,
    `synopsis`       TEXT,
    `background`     TEXT,
    `season`         TEXT,
    `year`           INTEGER,
    `broadcast`      TEXT,
    `date_created`   INTEGER                           NOT NULL,
    `date_modified`  INTEGER                           NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_anime_mal_id` ON `anime` (`mal_id`);

CREATE TABLE IF NOT EXISTS `tag`
(
    `tag_id`        INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `name`          TEXT                              NOT NULL COLLATE NOCASE,
    `date_created`  INTEGER                           NOT NULL,
    `date_modified` INTEGER                           NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_tag_name` ON `tag` (`name`);

CREATE INDEX IF NOT EXISTS `index_tag_date_created` ON `tag` (`date_created`);

CREATE INDEX IF NOT EXISTS `index_tag_date_modified` ON `tag` (`date_modified`);

CREATE TABLE IF NOT EXISTS `genre`
(
    `genre_id`      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `mal_id`        INTEGER                           NOT NULL,
    `type`          TEXT COLLATE NOCASE,
    `name`          TEXT COLLATE NOCASE,
    `url`           TEXT COLLATE NOCASE,
    `date_created`  INTEGER                           NOT NULL,
    `date_modified` INTEGER                           NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_genre_name` ON `genre` (`name`);

CREATE INDEX IF NOT EXISTS `index_genre_date_created` ON `genre` (`date_created`);

CREATE INDEX IF NOT EXISTS `index_genre_date_modified` ON `genre` (`date_modified`);

CREATE TABLE IF NOT EXISTS `studio`
(
    `studio_id`     INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `mal_id`        INTEGER                           NOT NULL,
    `type`          TEXT COLLATE NOCASE,
    `name`          TEXT COLLATE NOCASE,
    `url`           TEXT COLLATE NOCASE,
    `date_created`  INTEGER                           NOT NULL,
    `date_modified` INTEGER                           NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_studio_name` ON `studio` (`name`);

CREATE INDEX IF NOT EXISTS `index_studio_date_created` ON `studio` (`date_created`);

CREATE INDEX IF NOT EXISTS `index_studio_date_modified` ON `studio` (`date_modified`);

CREATE TABLE IF NOT EXISTS `theme`
(
    `theme_id`      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `mal_id`        INTEGER                           NOT NULL,
    `type`          TEXT COLLATE NOCASE,
    `name`          TEXT COLLATE NOCASE,
    `url`           TEXT COLLATE NOCASE,
    `date_created`  INTEGER                           NOT NULL,
    `date_modified` INTEGER                           NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_theme_name` ON `theme` (`name`);

CREATE INDEX IF NOT EXISTS `index_theme_date_created` ON `theme` (`date_created`);

CREATE INDEX IF NOT EXISTS `index_theme_date_modified` ON `theme` (`date_modified`);

CREATE TABLE IF NOT EXISTS `producer`
(
    `producer_id`   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `mal_id`        INTEGER                           NOT NULL,
    `type`          TEXT COLLATE NOCASE,
    `name`          TEXT COLLATE NOCASE,
    `url`           TEXT COLLATE NOCASE,
    `date_created`  INTEGER                           NOT NULL,
    `date_modified` INTEGER                           NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_producer_name` ON `producer` (`name`);

CREATE INDEX IF NOT EXISTS `index_producer_date_created` ON `producer` (`date_created`);

CREATE INDEX IF NOT EXISTS `index_producer_date_modified` ON `producer` (`date_modified`);

CREATE TABLE IF NOT EXISTS `licensor`
(
    `licensor_id`   INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `mal_id`        INTEGER                           NOT NULL,
    `type`          TEXT COLLATE NOCASE,
    `name`          TEXT COLLATE NOCASE,
    `url`           TEXT COLLATE NOCASE,
    `date_created`  INTEGER                           NOT NULL,
    `date_modified` INTEGER                           NOT NULL
);

CREATE UNIQUE INDEX IF NOT EXISTS `index_licensor_name` ON `licensor` (`name`);

CREATE INDEX IF NOT EXISTS `index_licensor_date_created` ON `licensor` (`date_created`);

CREATE INDEX IF NOT EXISTS `index_licensor_date_modified` ON `licensor` (`date_modified`);

CREATE TABLE IF NOT EXISTS `favorite`
(
    `favorite_id`    INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `user_id`        INTEGER                           NOT NULL,
    `anime_id`       INTEGER                           NOT NULL,
    `date_favorited` INTEGER                           NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`anime_id`) REFERENCES `anime` (`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_favorite_user_id` ON `favorite` (`user_id`);

CREATE INDEX IF NOT EXISTS `index_favorite_anime_id` ON `favorite` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_favorite_date_favorited` ON `favorite` (`date_favorited`);

CREATE TABLE IF NOT EXISTS `anime_tag`
(
    `anime_tag_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `user_id`      INTEGER,
    `anime_id`     INTEGER                           NOT NULL,
    `tag_id`       INTEGER                           NOT NULL,
    `date_tagged`  INTEGER                           NOT NULL,
    FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`anime_id`) REFERENCES `anime` (`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`tag_id`) REFERENCES `tag` (`tag_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_anime_tag_user_id` ON `anime_tag` (`user_id`);

CREATE INDEX IF NOT EXISTS `index_anime_tag_anime_id` ON `anime_tag` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_anime_tag_tag_id` ON `anime_tag` (`tag_id`);

CREATE INDEX IF NOT EXISTS `index_anime_tag_date_tagged` ON `anime_tag` (`date_tagged`);

CREATE TABLE IF NOT EXISTS `anime_genre`
(
    `anime_genre_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `anime_id`       INTEGER                           NOT NULL,
    `genre_id`       INTEGER                           NOT NULL,
    `date_added`     INTEGER                           NOT NULL,
    FOREIGN KEY (`anime_id`) REFERENCES `anime` (`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`genre_id`) REFERENCES `genre` (`genre_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_anime_genre_anime_id` ON `anime_genre` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_anime_genre_genre_id` ON `anime_genre` (`genre_id`);

CREATE INDEX IF NOT EXISTS `index_anime_genre_date_added` ON `anime_genre` (`date_added`);

CREATE TABLE IF NOT EXISTS `anime_studio`
(
    `anime_studio_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `anime_id`        INTEGER                           NOT NULL,
    `studio_id`       INTEGER                           NOT NULL,
    `date_added`      INTEGER                           NOT NULL,
    FOREIGN KEY (`anime_id`) REFERENCES `anime` (`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`studio_id`) REFERENCES `studio` (`studio_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_anime_studio_anime_id` ON `anime_studio` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_anime_studio_studio_id` ON `anime_studio` (`studio_id`);

CREATE INDEX IF NOT EXISTS `index_anime_studio_date_added` ON `anime_studio` (`date_added`);

CREATE TABLE IF NOT EXISTS `anime_theme`
(
    `anime_theme_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `anime_id`       INTEGER                           NOT NULL,
    `theme_id`       INTEGER                           NOT NULL,
    `date_added`     INTEGER                           NOT NULL,
    FOREIGN KEY (`anime_id`) REFERENCES `anime` (`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`theme_id`) REFERENCES `theme` (`theme_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_anime_theme_anime_id` ON `anime_theme` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_anime_theme_theme_id` ON `anime_theme` (`theme_id`);

CREATE INDEX IF NOT EXISTS `index_anime_theme_date_added` ON `anime_theme` (`date_added`);

CREATE TABLE IF NOT EXISTS `anime_producer`
(
    `anime_producer_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `anime_id`          INTEGER                           NOT NULL,
    `producer_id`       INTEGER                           NOT NULL,
    `date_added`        INTEGER                           NOT NULL,
    FOREIGN KEY (`anime_id`) REFERENCES `anime` (`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`producer_id`) REFERENCES `producer` (`producer_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_anime_producer_anime_id` ON `anime_producer` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_anime_producer_producer_id` ON `anime_producer` (`producer_id`);

CREATE INDEX IF NOT EXISTS `index_anime_producer_date_added` ON `anime_producer` (`date_added`);

CREATE TABLE IF NOT EXISTS `anime_licensor`
(
    `anime_licensor_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `anime_id`          INTEGER                           NOT NULL,
    `licensor_id`       INTEGER                           NOT NULL,
    `date_added`        INTEGER                           NOT NULL,
    FOREIGN KEY (`anime_id`) REFERENCES `anime` (`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE,
    FOREIGN KEY (`licensor_id`) REFERENCES `licensor` (`licensor_id`) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS `index_anime_licensor_anime_id` ON `anime_licensor` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_anime_licensor_licensor_id` ON `anime_licensor` (`licensor_id`);

CREATE INDEX IF NOT EXISTS `index_anime_licensor_date_added` ON `anime_licensor` (`date_added`);