-if class com.example.movie_catalog.data.api.home.seasons.SeasonDTO
-keepnames class com.example.movie_catalog.data.api.home.seasons.SeasonDTO
-if class com.example.movie_catalog.data.api.home.seasons.SeasonDTO
-keep class com.example.movie_catalog.data.api.home.seasons.SeasonDTOJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.example.movie_catalog.data.api.home.seasons.SeasonDTO
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.example.movie_catalog.data.api.home.seasons.SeasonDTO
-keepclassmembers class com.example.movie_catalog.data.api.home.seasons.SeasonDTO {
    public synthetic <init>(java.lang.Integer,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
