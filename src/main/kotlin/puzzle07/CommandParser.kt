package puzzle07

import java.math.BigInteger

internal class CommandParser {
    fun parseFileToCommands(fileContent: String) {
        val fileLines = fileContent
            .replace(CHARACTER_TO_REMOVE, "")
            .split(LINE_DELIMITER)
            .filter { line ->
                line.isNotBlank()
            }
            .map { line ->
                line.trim()
            }

        println("parseFileToCommands fileLines = $fileLines")
        val listOfCommands = getListOfCommands(fileLines = fileLines)

        val listOfPuzzleDirectories = parseListOfCommands(
            commands = listOfCommands
        )

        getDirectoriesAtMost(listOfPuzzleDirectories)
    }

    private fun getDirectoriesAtMost(
        listOfPuzzleDirectories: List<PuzzleFileSystem>,
        thresholdValue: BigInteger = BigInteger("100000")
    ) {
        // Clean
        val directories = listOfPuzzleDirectories
            .filterIsInstance<PuzzleFileSystem.PuzzleDirectory>()

        val rootDir = directories
            .first { directory ->
                directory.name == ROOT_DIR
            }

        cleanStuff(
            directory = rootDir,
            directories = directories,
        )

        // Get higher
        println("getDirectoriesAtMost rootDir = $rootDir")
        val listOfAtMost = mutableListOf<PuzzleFileSystem>()
        if (rootDir.size <= thresholdValue) {
            listOfAtMost += rootDir
        }
        parseDirectoryForThreshold(
            directory = rootDir,
            thresholdValue = thresholdValue,
            listOfAtMost = listOfAtMost
        )

        println("getDirectoriesAtMost listOfAtMost = $listOfAtMost")
        val sumOfAll = listOfAtMost.sumOf { fileSystem ->
            fileSystem.size
        }
        println("getDirectoriesAtMost sumOfAll = $sumOfAll")

        val freeSpace = FILESYSTEM_SIZE - rootDir.size
        val neededSpace = UNUSED_SPACE_NEEDED - freeSpace
        val listForNeededSpace = mutableListOf<PuzzleFileSystem.PuzzleDirectory>()
        if (rootDir.size >= neededSpace) {
            listForNeededSpace += rootDir
        }
        parseDirectoryForNeededSpace(
            rootDir,
            neededSpace,
            listForNeededSpace,
        )
        println("getDirectoriesAtMost listForNeededSpace = $listForNeededSpace")

        val smallest = listForNeededSpace.minByOrNull { directory ->
            directory.size
        }!!
        println("getDirectoriesAtMost smallest = $smallest")
    }

    private fun parseDirectoryForNeededSpace(
        directory: PuzzleFileSystem.PuzzleDirectory,
        neededSpace: BigInteger,
        listForNeededSpace: MutableList<PuzzleFileSystem.PuzzleDirectory>
    ) {
        directory.files.forEach { file ->
            if (file is PuzzleFileSystem.PuzzleDirectory
                && file.size >= neededSpace
            ) {
                listForNeededSpace += file
            }
            if (file is PuzzleFileSystem.PuzzleDirectory) {
                parseDirectoryForNeededSpace(
                    directory = file,
                    neededSpace = neededSpace,
                    listForNeededSpace = listForNeededSpace,
                )
            }
        }
    }

    private fun parseDirectoryForThreshold(
        directory: PuzzleFileSystem.PuzzleDirectory,
        thresholdValue: BigInteger,
        listOfAtMost: MutableList<PuzzleFileSystem>
    ) {
        directory.files.forEach { file ->
            if (file is PuzzleFileSystem.PuzzleDirectory
                && file.size <= thresholdValue
            ) {
                listOfAtMost += file
            }
            if (file is PuzzleFileSystem.PuzzleDirectory) {
                parseDirectoryForThreshold(
                    directory = file,
                    thresholdValue = thresholdValue,
                    listOfAtMost = listOfAtMost,
                )
            }
        }
    }

    private fun cleanStuff(
        directory: PuzzleFileSystem.PuzzleDirectory,
        directories: List<PuzzleFileSystem.PuzzleDirectory>
    ) {
        directory
            .files
            .forEachIndexed { index, puzzleFileSystem ->
                if (puzzleFileSystem is PuzzleFileSystem.PuzzleDirectory) {
                    directory.files[index] = directories.first { directory ->
                        directory.name == puzzleFileSystem.name
                    }
                    cleanStuff(
                        directory = directory.files[index] as PuzzleFileSystem.PuzzleDirectory,
                        directories = directories,
                    )
                }
            }
    }

    private fun getListOfCommands(fileLines: List<String>): MutableList<MutableList<String>> {
        println("parseFileLines")
        var listOfCommands = mutableListOf<MutableList<String>>()
        var indexOfPairs = -1
        var command = false

        fileLines
            .forEachIndexed { _, line ->
                if (line.isCommand()
                    && !command
                ) {
                    command = true
                    listOfCommands += mutableListOf<String>()

                    indexOfPairs += 1
                    listOfCommands[indexOfPairs] += line
                } else if (line.isCommand()) {
                    listOfCommands[indexOfPairs] += line
                } else {
                    command = false
                    listOfCommands[indexOfPairs] += line
                }
            }

        return listOfCommands
    }

    private fun parseListOfCommands(commands: List<List<String>>): List<PuzzleFileSystem> {
        var currentDirectoryName = ""
        val directories = commands
            .map { directories ->
                directories
                    .filter { line ->
                        line.isCommand() && line.contains(PREVIOUS_DIRECTORY)
                    }
                    .forEach {
                        currentDirectoryName = currentDirectoryName.substring(
                            startIndex = 0,
                            endIndex = currentDirectoryName.lastIndexOf("/"),
                        )
                    }
                val newDirectory =
                    parseDirectory(parentDirectoryName = currentDirectoryName, directoryCommands = directories)
                currentDirectoryName = newDirectory.name
                newDirectory
            }

        return directories
    }

    private fun parseDirectory(
        parentDirectoryName: String,
        directoryCommands: List<String>
    ): PuzzleFileSystem {
        val directoryName = directoryCommands
            .filter { line ->
                line.isCommand()
            }
            .first { line ->
                val dirName = if (line.contains("cd")) {
                    line.substring(
                        startIndex = line.lastIndexOf(" "),
                    )
                } else {
                    ""
                }.trim()
                dirName.isNotBlank()
                        && dirName != PREVIOUS_DIRECTORY
            }
            .run {
                val dirName = if (this.contains("cd")) {
                    this.substring(
                        startIndex = this.lastIndexOf(" "),
                    )
                } else {
                    ""
                }.trim()
                if (dirName == ROOT_DIR) {
                    dirName
                } else if (parentDirectoryName == ROOT_DIR) {
                    "${parentDirectoryName}$dirName"
                } else {
                    "$parentDirectoryName/$dirName"
                }
            }

        val files = getListOfFiles(directoryName = directoryName, directoryInfos = directoryCommands)

        return PuzzleFileSystem.PuzzleDirectory(
            name = directoryName,
            files = files
        )
    }

    private fun getListOfFiles(directoryName: String, directoryInfos: List<String>): MutableList<PuzzleFileSystem> {
        val files = directoryInfos
            .filter { line ->
                !line.isCommand()
            }
            .map { line ->
                val name = line.substring(
                    startIndex = line.lastIndexOf(" ")
                ).trim()
                if (line.isDirectory()) {
                    PuzzleFileSystem.PuzzleDirectory(
                        name = if (directoryName == ROOT_DIR) {
                            "/$name"
                        } else {
                            "$directoryName/$name"
                        },
                        files = mutableListOf(),
                    )
                } else {
                    PuzzleFileSystem.PuzzleFile(
                        name = name,
                        size = line.substring(
                            startIndex = 0,
                            endIndex = line.indexOf(" ")
                        ).toBigInteger(),
                    )
                }
            }
            .toMutableList()
        return files
    }

    private fun String.isCommand(): Boolean = this.startsWith(COMMAND_PREFIX)

    private fun String.isDirectory(): Boolean = this.startsWith(DIRECTORY_PREFIX)

    companion object {
        private const val CHARACTER_TO_REMOVE = "\r"
        private const val LINE_DELIMITER = "\n"
        private const val COMMAND_PREFIX = "$"
        private const val DIRECTORY_PREFIX = "dir"
        private const val ROOT_DIR = "/"
        private const val PREVIOUS_DIRECTORY = ".."
        private val FILESYSTEM_SIZE = BigInteger.valueOf(70_000_000L)
        private val UNUSED_SPACE_NEEDED = BigInteger.valueOf(30_000_000L)
    }
}