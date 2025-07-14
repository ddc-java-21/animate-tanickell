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
- [`AnimeTag`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/AnimeTag.java)
- [`AnimeGenre`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/AnimeGenre.java)
- [`Favorite`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Favorite.java)
- [`Genre`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Genre.java)
- [`Studio`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Studio.java)
- [`Tag`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/Tag.java)
- [`User`](https://github.com/ddc-java-21/animate-tanickell/blob/main/app/src/main/java/edu/cnm/deepdive/animate/model/entity/User.java)





