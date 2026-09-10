# Mantém modelos de dados para Gson sob R8
-keep class com.fasttrade.android.network.** { *; }
-keepclassmembers class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepattributes Signature, *Annotation*
