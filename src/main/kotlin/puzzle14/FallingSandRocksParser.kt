package puzzle14

import FileUtils

class FallingSandRocksParser {
    fun parseFileToRocks(fileContent: String) {
        println("parseFileToRocks")
        val fileLines = FileUtils
            .splitStringWithDelimiter(
                fileContent = fileContent,
            )
    }
}