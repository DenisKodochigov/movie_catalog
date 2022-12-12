-if class com.example.movie_catalog.data.api.film_info.SimilarDTO
-keepnames class com.example.movie_catalog.data.api.film_info.SimilarDTO
-if class com.example.movie_catalog.data.api.film_info.SimilarDTO
-keep class com.example.movie_catalog.data.api.film_info.SimilarDTOJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.example.movie_catalog.data.api.film_info.SimilarDTO
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.example.movie_catalog.data.api.film_info.SimilarDTO
-keepclassmembers class com.example.movie_catalog.data.api.film_info.SimilarDTO {
    public synthetic <init>(java.lang.Integer,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
