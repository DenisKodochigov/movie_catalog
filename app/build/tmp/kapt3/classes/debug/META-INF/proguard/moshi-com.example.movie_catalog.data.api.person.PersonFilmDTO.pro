-if class com.example.movie_catalog.data.api.person.PersonFilmDTO
-keepnames class com.example.movie_catalog.data.api.person.PersonFilmDTO
-if class com.example.movie_catalog.data.api.person.PersonFilmDTO
-keep class com.example.movie_catalog.data.api.person.PersonFilmDTOJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.example.movie_catalog.data.api.person.PersonFilmDTO
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.example.movie_catalog.data.api.person.PersonFilmDTO
-keepclassmembers class com.example.movie_catalog.data.api.person.PersonFilmDTO {
    public synthetic <init>(java.lang.Integer,java.lang.String,java.lang.String,java.lang.String,java.lang.Boolean,java.lang.String,java.lang.String,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
