-if class com.example.movie_catalog.data.api.home.premieres.FilmDTO
-keepnames class com.example.movie_catalog.data.api.home.premieres.FilmDTO
-if class com.example.movie_catalog.data.api.home.premieres.FilmDTO
-keep class com.example.movie_catalog.data.api.home.premieres.FilmDTOJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.example.movie_catalog.data.api.home.premieres.FilmDTO
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.example.movie_catalog.data.api.home.premieres.FilmDTO
-keepclassmembers class com.example.movie_catalog.data.api.home.premieres.FilmDTO {
    public synthetic <init>(java.lang.Integer,java.lang.String,java.lang.String,java.lang.Integer,java.lang.String,java.lang.String,java.util.List,java.util.List,java.lang.Integer,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
