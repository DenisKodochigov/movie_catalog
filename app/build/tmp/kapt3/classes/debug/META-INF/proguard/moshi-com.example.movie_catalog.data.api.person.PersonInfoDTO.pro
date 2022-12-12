-if class com.example.movie_catalog.data.api.person.PersonInfoDTO
-keepnames class com.example.movie_catalog.data.api.person.PersonInfoDTO
-if class com.example.movie_catalog.data.api.person.PersonInfoDTO
-keep class com.example.movie_catalog.data.api.person.PersonInfoDTOJsonAdapter {
    public <init>(com.squareup.moshi.Moshi);
}
-if class com.example.movie_catalog.data.api.person.PersonInfoDTO
-keepnames class kotlin.jvm.internal.DefaultConstructorMarker
-if class com.example.movie_catalog.data.api.person.PersonInfoDTO
-keepclassmembers class com.example.movie_catalog.data.api.person.PersonInfoDTO {
    public synthetic <init>(java.lang.Integer,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.Integer,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.util.List,java.lang.Integer,java.lang.String,java.util.List,java.util.List,int,kotlin.jvm.internal.DefaultConstructorMarker);
}
