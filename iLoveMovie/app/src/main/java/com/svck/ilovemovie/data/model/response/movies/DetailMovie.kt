package com.svck.ilovemovie.data.model.response.movies

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMovie (
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdrop_path: String?,
    @SerializedName("belongs_to_collection") val belongs_to_collection: MovieCollection?,
    @SerializedName("budget") val budget: Int,
    @SerializedName("genres") val genres: List<GenresMovie>,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("id") val id: Int,
    @SerializedName("imdb_id") val imdb_id: String,
    @SerializedName("original_language") val original_language: String,
    @SerializedName("original_title") val original_title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val poster_path: String?,
    @SerializedName("production_companies") val production_companies: List<ProductionCompanies>,
    @SerializedName("production_countries") val production_countries: List<ProductionCountries>,
    @SerializedName("release_date") val release_date: String,
    @SerializedName("revenue") val revenue: Int,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("spoken_languages") val spoken_languages: List<SpokenLanguage>,
    @SerializedName("status") val status: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("title") val title: String,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("vote_count") val vote_count: Double,


) : Parcelable {

    @Parcelize
    data class MovieCollection(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
        @SerializedName("poster_path") val poster_path: String,
        @SerializedName("backdrop_path") val backdrop_path: String,
    ) : Parcelable

    @Parcelize
    data class GenresMovie(
        @SerializedName("id") val id: Int,
        @SerializedName("name") val name: String,
    ) : Parcelable

    @Parcelize
    data class ProductionCompanies(
        @SerializedName("id") val id: Int,
        @SerializedName("logo_path") val logo_path: String,
        @SerializedName("name") val name: String,
        @SerializedName("origin_country") val origin_country: String,
    ) : Parcelable

    @Parcelize
    data class ProductionCountries(
        @SerializedName("iso_3166_1") val iso_3166_1: String,
        @SerializedName("name") val name: String,
    ) : Parcelable

    @Parcelize
    data class SpokenLanguage(
        @SerializedName("iso_639_1") val iso_639_1: String,
        @SerializedName("english_name") val english_name: String,
        @SerializedName("name") val name: String,
    ) : Parcelable
}