import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

class FileUtils(
    private val inputFileName: String,
    private val inputFileNameExample: String,
) {
    fun getFileContentFromFile(): String? {
        return readFileContent(
            fileName = inputFileName,
        )
    }

    fun getFileContentFromFileExample(): String? {
        return readFileContent(
            fileName = inputFileNameExample,
        )
    }

    private fun readFileContent(fileName: String): String? {
        val filePath = Path(fileName)
            .takeIf { file ->
                file.exists()
            }

        return if (filePath != null) {
            Files.readString(
                filePath
            )
        } else {
            null
        }
    }
}