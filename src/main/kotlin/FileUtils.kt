import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

class FileUtils(
    private val inputFileName: String,
    private val inputFileNameExample: String,
) {
    fun getFileContentFromFile(): String? {
        println("getFileContentFromFile Try to open file $inputFileName")
        return readFileContent(
            fileName = inputFileName,
        )
    }

    fun getFileContentFromFileExample(): String? {
        println("getFileContentFromFileExample Try to open file $inputFileNameExample")
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
            println("readFileContent file $fileName exists")
            Files.readString(
                filePath
            )
        } else {
            println("readFileContent file $fileName does not exists")
            null
        }
    }

    companion object {
        fun fileToLines(fileContent: String, delimiter: String = NEW_LINE_DELIMITER): List<String> {
            return fileContent.replace(CHARACTER_TO_REMOVE, EMPTY_CHARACTER)
                .split(delimiter)
                .map { line ->
                    line.trim()
                }
                .filter { line ->
                    line.isNotBlank()
                }
        }

        private const val CHARACTER_TO_REMOVE = "\r"
        private const val EMPTY_CHARACTER = ""
        private const val NEW_LINE_DELIMITER = "\n"
    }
}