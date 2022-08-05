package com.linya.mirai.service.netcloud

import kotlinx.serialization.Serializable

@Serializable
data class SearchResultsJson(
    val code: Int,
    val result: Result
)

@Serializable
data class Result(
    val songCount: Int,
    val songs: List<Song>
)

@Serializable
data class Song(
    val album: Album,
    val alias: List<String>,
    val artists: List<ArtistX>,
    val copyrightId: Int,
    val duration: Int,
    val fee: Int,
    val ftype: Int,
    val id: Int,
    val mark: Long,
    val mvid: Int,
    val name: String,
    val rUrl: String? = null,
    val rtype: Int,
    val status: Int
)

@Serializable
data class Album(
    val artist: Artist,
    val copyrightId: Int,
    val id: Int,
    val mark: Int,
    val name: String,
    val picId: Long,
    val publishTime: Long,
    val size: Int,
    val status: Int
)

@Serializable
data class ArtistX(
    val albumSize: Int,
    val alias: List<String>,
    val id: Int,
    val img1v1: Int,
    val img1v1Url: String,
    val name: String,
    val picId: Int,
    val picUrl: String? = null,
    val trans: String? = null,
)

@Serializable
data class Artist(
    val albumSize: Int,
    val alias: List<String>,
    val id: Int,
    val img1v1: Int,
    val img1v1Url: String,
    val name: String,
    val picId: Int,
    val picUrl: String? = null,
    val trans: String? = null,
)