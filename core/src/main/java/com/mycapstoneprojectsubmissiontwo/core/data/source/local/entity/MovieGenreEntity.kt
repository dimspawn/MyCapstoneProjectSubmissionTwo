package com.mycapstoneprojectsubmissiontwo.core.data.source.local.entity

import android.os.Parcelable
import androidx.room.*
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "genre",
    foreignKeys = [ForeignKey(entity = MovieEntity::class,
    parentColumns = ["movieId"],
    childColumns = ["movieId"],
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE)],
    indices = [Index(value = ["movieId"])]
)
data class MovieGenreEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "genreId")
    var genreId: Int = 0,

    @ColumnInfo(name = "movieId")
    var movieId: Int,

    @ColumnInfo(name = "genreName")
    var genreName: String
): Parcelable