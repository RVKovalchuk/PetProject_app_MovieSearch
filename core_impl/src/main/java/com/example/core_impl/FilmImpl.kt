package com.example.core_impl

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.domain.Film
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
//Класс используется для создании таблицы
@Entity(
    tableName = FilmImpl.Fields.TABLE_NAME_FOR_FILMS_DB,
    indices = [Index(value = [FilmImpl.Fields.TITLE], unique = true)]
)
//Имплементация класса Film
data class FilmImpl(
    @PrimaryKey(autoGenerate = true) @SerializedName(Fields.ID) override val id: Int = 0,
    @ColumnInfo(name = Fields.TITLE) @SerializedName(Fields.TITLE) override val title: String?,
    @ColumnInfo(name = Fields.POSTER_PATH) @SerializedName(Fields.POSTER_PATH) override val poster: String?,
    @ColumnInfo(name = Fields.DESCRIPTION) @SerializedName(Fields.DESCRIPTION) override val description: String?,
    override var isInFavorites: Boolean = false,
    @ColumnInfo(name = Fields.VOTE_AVERAGE) @SerializedName(Fields.VOTE_AVERAGE) override val rating: Double = 0.0,
    @ColumnInfo(name = Fields.WATCH_LATER_TIME) @SerializedName(Fields.WATCH_LATER_TIME) override var timeWatchLater: Long = 0
) : Film, Parcelable {
    object Fields {
        const val TABLE_NAME_FOR_FILMS_DB = "cashed_films"
        const val TITLE = "title"
        const val POSTER_PATH = "poster_path"
        const val DESCRIPTION = "overview"
        const val VOTE_AVERAGE = "vote_average"
        const val WATCH_LATER_TIME = "watch_later_time"
        const val ID = "id"
    }
}

fun Film.toImpl(): FilmImpl {
    return if (this is FilmImpl) this
    else FilmImpl(
        id = this.id,
        title = this.title,
        poster = this.poster,
        description = this.description,
        isInFavorites = this.isInFavorites,
        rating = this.rating,
        timeWatchLater = this.timeWatchLater,
    )
}


