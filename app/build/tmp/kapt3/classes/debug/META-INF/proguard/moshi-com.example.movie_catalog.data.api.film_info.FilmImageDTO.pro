-if class com.example.movie_catalog.data.api.film_info.FilmImageDTO
-keepnames class com.example.movie_catalog.data.api.film_info.FilmImageDTO
-if class com.example.movie_catalog.data.api.film_info.FilmImageDTO
-keep class com.example.movie_catalog.data.api.film_info.FilmImageDTOJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.example.movie_catalog.data.api.film_info.FilmImageDTO
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.example.movie_catalog.data.api.film_info.FilmImageDTO
-keepclassmembers class com.example.movie_catalog.data.api.film_info.FilmImageDTO {
    public synthetic <init>(java.lang.Integer,java.lang.Integer,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
