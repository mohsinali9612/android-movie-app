import java.util.Properties
import java.io.FileInputStream

val localProperties = Properties().apply {
    val localPropertiesFile = rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        load(FileInputStream(localPropertiesFile))
    }
}

object BuildConfig {
    const val TMDB_API_KEY = "\"${localProperties.getProperty("TMDB_API_KEY")}\""
} 