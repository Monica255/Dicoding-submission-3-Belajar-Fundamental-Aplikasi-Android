package com.example.aplikasigit3

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchResponse(
    @SerializedName("total_count")
    var totalCount: Int,

    @SerializedName("incomplete_results")
    var incompleteResults: Boolean,

    var items: List<User>
)

@Entity
@Parcelize
data class User(
    @PrimaryKey
    @ColumnInfo(name = "login")
    var login: String,

    @ColumnInfo(name = "id")
    var id: String,
    
    @ColumnInfo(name = "avatarUrl")
    @SerializedName("avatar_url")
    var avatarUrl: String
) : Parcelable

data class DetailUser(
    var login: String,
    var name: String,
    var location: String,

    @SerializedName("public_repos")
    var publicRepos: String,

    var company: String,
    var followers: String,
    var following: String,

    @SerializedName("avatar_url")
    var avatarUrl: String
)


