-- Generated 2025-07-13 16:19:37-0600 for database version 1

CREATE TABLE IF NOT EXISTS `user` (`user_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `oauth_key` TEXT NOT NULL, `display_name` TEXT NOT NULL COLLATE NOCASE, `date_created` INTEGER NOT NULL, `date_modified` INTEGER NOT NULL);

CREATE UNIQUE INDEX IF NOT EXISTS `index_user_oauth_key` ON `user` (`oauth_key`);

CREATE UNIQUE INDEX IF NOT EXISTS `index_user_display_name` ON `user` (`display_name`);

CREATE TABLE IF NOT EXISTS `anime` (`anime_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `mal_id` INTEGER, `mal_url` TEXT, `poster_url` TEXT, `trailer_url` TEXT, `title` TEXT COLLATE NOCASE, `title_english` TEXT COLLATE NOCASE, `title_japanese` TEXT COLLATE NOCASE, `type` TEXT, `source` TEXT, `episodes` INTEGER, `status` TEXT, `airing` INTEGER, `date_released` INTEGER, `date_finished` INTEGER, `aired_summary` TEXT, `duration` TEXT, `rating` TEXT, `score` REAL, `rank` INTEGER, `popularity` INTEGER, `synopsis` TEXT, `background` TEXT, `season` TEXT, `year` INTEGER, `broadcast` TEXT, `date_created` INTEGER NOT NULL, `date_modified` INTEGER NOT NULL);

CREATE UNIQUE INDEX IF NOT EXISTS `index_anime_mal_id` ON `anime` (`mal_id`);

CREATE TABLE IF NOT EXISTS `tag` (`tag_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL COLLATE NOCASE, `date_created` INTEGER NOT NULL, `date_modified` INTEGER NOT NULL);

CREATE UNIQUE INDEX IF NOT EXISTS `index_tag_name` ON `tag` (`name`);

CREATE INDEX IF NOT EXISTS `index_tag_date_created` ON `tag` (`date_created`);

CREATE INDEX IF NOT EXISTS `index_tag_date_modified` ON `tag` (`date_modified`);

CREATE TABLE IF NOT EXISTS `favorite` (`favorite_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `anime_id` INTEGER NOT NULL, `date_favorited` INTEGER NOT NULL, FOREIGN KEY(`user_id`) REFERENCES `user`(`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`anime_id`) REFERENCES `anime`(`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE );

CREATE INDEX IF NOT EXISTS `index_favorite_user_id` ON `favorite` (`user_id`);

CREATE INDEX IF NOT EXISTS `index_favorite_anime_id` ON `favorite` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_favorite_date_favorited` ON `favorite` (`date_favorited`);

CREATE TABLE IF NOT EXISTS `anime_tag` (`anime_tag_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER, `anime_id` INTEGER NOT NULL, `tag_id` INTEGER NOT NULL, `date_tagged` INTEGER NOT NULL, FOREIGN KEY(`user_id`) REFERENCES `user`(`user_id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`anime_id`) REFERENCES `anime`(`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`tag_id`) REFERENCES `tag`(`tag_id`) ON UPDATE NO ACTION ON DELETE CASCADE );

CREATE INDEX IF NOT EXISTS `index_anime_tag_user_id` ON `anime_tag` (`user_id`);

CREATE INDEX IF NOT EXISTS `index_anime_tag_anime_id` ON `anime_tag` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_anime_tag_tag_id` ON `anime_tag` (`tag_id`);

CREATE INDEX IF NOT EXISTS `index_anime_tag_date_tagged` ON `anime_tag` (`date_tagged`);

CREATE TABLE IF NOT EXISTS `genre` (`genre_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT NOT NULL COLLATE NOCASE, `date_created` INTEGER NOT NULL, `date_modified` INTEGER NOT NULL);

CREATE UNIQUE INDEX IF NOT EXISTS `index_genre_name` ON `genre` (`name`);

CREATE INDEX IF NOT EXISTS `index_genre_date_created` ON `genre` (`date_created`);

CREATE INDEX IF NOT EXISTS `index_genre_date_modified` ON `genre` (`date_modified`);

CREATE TABLE IF NOT EXISTS `anime_genre` (`anime_genre_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `anime_id` INTEGER NOT NULL, `genre_id` INTEGER NOT NULL, `date_added` INTEGER NOT NULL, FOREIGN KEY(`anime_id`) REFERENCES `anime`(`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE , FOREIGN KEY(`genre_id`) REFERENCES `genre`(`genre_id`) ON UPDATE NO ACTION ON DELETE CASCADE );

CREATE INDEX IF NOT EXISTS `index_anime_genre_anime_id` ON `anime_genre` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_anime_genre_genre_id` ON `anime_genre` (`genre_id`);

CREATE INDEX IF NOT EXISTS `index_anime_genre_date_added` ON `anime_genre` (`date_added`);

CREATE TABLE IF NOT EXISTS `studio` (`studio_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `anime_id` INTEGER NOT NULL, `name` TEXT NOT NULL COLLATE NOCASE, `date_created` INTEGER NOT NULL, `date_modified` INTEGER NOT NULL, FOREIGN KEY(`anime_id`) REFERENCES `anime`(`anime_id`) ON UPDATE NO ACTION ON DELETE CASCADE );

CREATE INDEX IF NOT EXISTS `index_studio_anime_id` ON `studio` (`anime_id`);

CREATE INDEX IF NOT EXISTS `index_studio_date_created` ON `studio` (`date_created`);

CREATE INDEX IF NOT EXISTS `index_studio_date_modified` ON `studio` (`date_modified`);