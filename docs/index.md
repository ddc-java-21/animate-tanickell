---
title: Overview
description: "Project proposal or summary of in-progress/completed project."
order: 0
---

{% include ddc-abbreviations.md %}

## Page contents
{:.no_toc}

- ToC
{:toc}

## Summary

AniMate is an anime media browser application for Android that provides a 
convenient location to view various types of information about existing and
upcoming Japanese animation content. It retrieves information about anime movies, 
TV shows, dramas, and more, and displays them to the user in a list of
interactive entries that, when tapped, reveal detailed information for that 
content, including synopses and other details like animation studio, images, 
trailers (youtube), air date, and more. For visual content, the user can then
tap again to proceed to an additional view focusing on that content, giving
them additional options to share media, download media, or open in an external 
app. 

In addition to media, the main anime listing view provides filtering options
to search content by name, or to filter results to the user by genre or by 
season/year. It also allows users to add favorites of content, and to provide
tags for sorting and retrieval at a later date.

The app stores an initial inventory of anime for a subset of shows, such as the 
currently airing season or the thirty-or-so newest entries available in the API; 
this set is what is stored initially in the database, and any other results that 
the user obtains are then added to this set. This allows for offline viewing of 
show information that has been saved locally, without the use of the external API.


## Intended users and user stories

* Casual watchers of TV shows and media who like to browse visually when deciding 
on what they should watch next.

    > As someone who enjoys browsing a visual database to discover new shows, I 
use this app to list anime by genres I like, so that I can more easily decide on 
new things to try that more closely match my tastes and interests.
  
* Anime enthusiasts who want to filter or sort shows by specific attributes to find 
information about them more quickly using a mobile interface.

    > As an avid anime consumer who needs to know what the latest releases are, I
find this mobile app a convenient way to quickly look up shows by release date, 
read their plot synopses, and watch trailers for them so that I can quickly 
prioritize the shows I want to watch this season, all without leaving my phone.

## Functionality

* Display a list of Anime media (shows, OVAs, and movies) to the user from a
landing 'page' (i.e., the main view), distinguished by poster image, title 
information, season and year information, and plot synopses
* Allow users to tap an anime to take them to a new view that shows a larger
image (poster), links to external content (e.g. MyAnimeList), and extended
synopses and other information about the entry
* Logging in using Google Sign-In, allowing for personalization of the in-app 
experience:
    * Marking apps as favorites that can be listed in a menu and revisited later
    * Tagging entries with tags for personal sorting
* Sorting the entries listed in the main view by anime attributes, including:
    * Listing by genre
    * Listing by season and year
    * Listing by newly released
* Embedded video (youtube video links) to watch trailers of anime listings, when
available


## Persistent data
  
* User information pertaining to Google Sign-In
    * Display name
    * OAuth2.0 identifier
    * Timestamp of first login to the app
* Anime retrieved from MyAnimeList via Jikan and cached locally for later viewing:
    * Anime titles, synopses, and descriptive information like genre and season
    * Anime from any list sort actions, including by genre, new release, etc.
* Information that pertains to user actions in the app, including:
    * Favorites (Anime)
    * Tags (User-provided)
* Information on genre and studio (any information that can be multiple for any
given anime)

    
## Device/external services

* Jikan API: https://jikan.moe/

## Stretch goals and possible enhancements 

1. Sorting (or populating) main list by more than just one attribute at a time
2. Sorting by more than just genre, or by season & year
3. Adding licensors, producers, and themes (each require additional db tables and/or bridge entities)
   --> Licensor, Producer, Theme, AnimeTheme
4. Option to download multiple image types (small image, big image, etc.)
5. Add avatar to user and allow for uploading picture
6. Create separate views for specific episodes
7. Ability to leave comments on anime episodes (for personal review)
8. (Larger Stretch) Development of a server application to host comments for 
multiple users at once, like a message board or reddit-style comment section