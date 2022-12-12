-if class com.example.movie_catalog.data.api.home.seasons.SeasonsDTO
-keepnames class com.example.movie_catalog.data.api.home.seasons.SeasonsDTO
-if class com.example.movie_catalog.data.api.home.seasons.SeasonsDTO
-keep class com.example.movie_catalog.data.api.home.seasons.SeasonsDTOJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.example.movie_catalog.data.api.home.seasons.SeasonsDTO
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.example.movie_catalog.data.api.home.seasons.SeasonsDTO
-keepclassmembers class com.example.movie_catalog.data.api.home.seasons.SeasonsDTO {
    public synthetic <init>(java.lang.Integer,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
