-if class com.example.movie_catalog.data.api.home.premieres.PremieresDTO
-keepnames class com.example.movie_catalog.data.api.home.premieres.PremieresDTO
-if class com.example.movie_catalog.data.api.home.premieres.PremieresDTO
-keep class com.example.movie_catalog.data.api.home.premieres.PremieresDTOJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.example.movie_catalog.data.api.home.premieres.PremieresDTO
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.example.movie_catalog.data.api.home.premieres.PremieresDTO
-keepclassmembers class com.example.movie_catalog.data.api.home.premieres.PremieresDTO {
    public synthetic <init>(java.lang.Integer,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
