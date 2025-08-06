---
title: Data Model
description: "UML class diagram, entity-relationship diagram, and DDL."
order: 20
---

{% include ddc-abbreviations.md %}

## Page contents
{:.no_toc:}

- ToC
{:toc}

## UML class diagram

[![AniMate UML Class Diagram](img/AniMate-UML.drawio.svg)](pdf/AniMate-UML.drawio.pdf)

## ERD entity diagram

[![AniMate ERD Entity Diagram](img/AniMate-ERD.drawio.svg)](pdf/AniMate-ERD.drawio.pdf)

## Data Definition Language code

{% include linked-file.md file="sql/ddl.sql" type="sql" %}


## Implementation


### Entity classes

- [`Anime`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Anime.java)
- [`AnimeGenre`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/AnimeGenre.java)
- [`AnimeLicensor`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/AnimeLicensor.java)
- [`AnimeProducer`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/AnimeProducer.java)
- [`AnimeStudio`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/AnimeStudio.java)
- [`AnimeTag`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/AnimeTag.java)
- [`AnimeTheme`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/AnimeTheme.java)
- [`Favorite`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Favorite.java)
- [`Genre`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Genre.java)
- [`Licensor`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Licensor.java)
- [`Producer`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Producer.java)
- [`Studio`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Studio.java)
- [`Tag`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Tag.java)
- [`Theme`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Theme.java)
- [`User`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/User.java)





