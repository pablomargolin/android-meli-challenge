# --- General Android & Kotlin ---
-keepattributes Signature, Annotation, EnclosingMethod, InnerClasses
-keepattributes SourceFile, LineNumberTable

# --- Moshi ---
-dontwarn com.squareup.moshi.**

-keepclassmembers class * {
    @com.squareup.moshi.Json <fields>;
}
-keep class com.squareup.moshi.KotlinJsonAdapterFactory

# DTOs
-keep class **.dto.** { *; }

# --- Retrofit ---
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# --- Hilt / Dagger ---
-dontwarn dagger.hilt.android.internal.**

# --- Timber ---
-keep class timber.log.** { *; }
-dontwarn timber.log.**

# --- Coil ---
-dontwarn coil3.**

# --- Domain Models ---
-keep class com.margo.domain.model.** { *; }
