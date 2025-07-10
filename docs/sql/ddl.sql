-- Generated 2025-07-10 10:34:29-0600 for database version 1

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
    `anime_id`      INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `title`         TEXT COLLATE NOCASE,
    `genre`         TEXT COLLATE NOCASE,
    `rating`        TEXT COLLATE NOCASE,
    `score`         REAL                              NOT NULL,
    `description`   TEXT,
    `posterUrl`     TEXT,
    `trailerUrl`    TEXT,
    `release_date`  INTEGER,
    `date_created`  INTEGER                           NOT NULL,
    `date_modified` INTEGER                           NOT NULL,
    `media_type`    TEXT,
    `copyright`     TEXT
);

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
    `user_id`      INTEGER                           NOT NULL,
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