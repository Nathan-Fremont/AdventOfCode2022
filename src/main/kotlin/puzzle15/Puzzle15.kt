package puzzle15

import FileUtils

private const val INPUT_FILE_NAME = "inputPuzzle15"
private const val INPUT_FILE_NAME_EXAMPLE = "inputPuzzle15Example"
private val fileUtils = FileUtils(
    inputFileName = INPUT_FILE_NAME,
    inputFileNameExample = INPUT_FILE_NAME_EXAMPLE,
)
private val sensorsParser = SensorsParser()
private val sensorsExecutor = SensorsExecutor()

fun main(args: Array<String>) {
    println("main")
    fileUtils.getFileContentFromFileExample()
        ?.also { fileContent ->
            val sensorsParserResult = sensorsParser.parseFileContentToListOfSensors(
                fileContent = fileContent,
            )

            sensorsExecutor.createGridFromSensorsAndBeacons(
                sensorsParserResult = sensorsParserResult,
            )
            val sabGrid = sensorsExecutor.calculateGridPossiblePositionsForMoreBeaconsInRow(row = 10)//2_000_000)

            // Part 1 :
            val numberOfNotPossibleBeaconsForPart1 = sabGrid
                .gridSquares
                .last()
                .filter { sabSquare ->
                    sabSquare is SaBGridSquare.None && !sabSquare.isBeaconPossible
                }
            println("main ${"numberOfNotPossibleBeaconsForPart1" to numberOfNotPossibleBeaconsForPart1.size}")
        }
}