-if class com.example.movie_catalog.data.api.home.top.TopFilmsDTO
-keepnames class com.example.movie_catalog.data.api.home.top.TopFilmsDTO
-if class com.example.movie_catalog.data.api.home.top.TopFilmsDTO
-keep class com.example.movie_catalog.data.api.home.top.TopFilmsDTOJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.example.movie_catalog.data.api.home.top.TopFilmsDTO
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.example.movie_catalog.data.api.home.top.TopFilmsDTO
-keepclassmembers class com.example.movie_catalog.data.api.home.top.TopFilmsDTO {
    public synthetic <init>(java.lang.Integer,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
