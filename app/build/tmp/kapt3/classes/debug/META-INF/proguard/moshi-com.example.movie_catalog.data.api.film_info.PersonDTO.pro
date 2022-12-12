-if class com.example.movie_catalog.data.api.film_info.PersonDTO
-keepnames class com.example.movie_catalog.data.api.film_info.PersonDTO
-if class com.example.movie_catalog.data.api.film_info.PersonDTO
-keep class com.example.movie_catalog.data.api.film_info.PersonDTOJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.example.movie_catalog.data.api.film_info.PersonDTO
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.example.movie_catalog.data.api.film_info.PersonDTO
-keepclassmembers class com.example.movie_catalog.data.api.film_info.PersonDTO {
    public synthetic <init>(java.lang.Integer,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
