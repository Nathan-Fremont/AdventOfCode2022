package puzzle08

import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

private const val INPUT_FILE_NAME = "inputPuzzle08"
private val treesGridHelper = TreesGridHelper()

fun main(args: Array<String>) {
    println("Try to open file $INPUT_FILE_NAME")

    Path(INPUT_FILE_NAME)
        .takeIf { file ->
            file.exists()
        }
        ?.also { file ->
            println("Parse file")
            val fileContent = Files.readString(
                file
            )

            val gridOfTrees = treesGridHelper.parseFileContentAsTreeGrid(
                fileContent = fileContent,
            )

            val flattenGrid = gridOfTrees
                .flatten()

            val visibleFromOutside = flattenGrid
                .filter { tree ->
                    tree.isVisibleFromOutside()
                }

            val edge = visibleFromOutside
                .filter { tree ->
                    tree.x == 0
                            || tree.y == 0
                            || tree.x == gridOfTrees.size - 1
                            || tree.y == gridOfTrees.size - 1
                }

            val notEdge = visibleFromOutside - edge.toSet()

            println("${"visibleFromOutside" to visibleFromOutside}")
            println("${"edge" to edge}")
            println("${"notEdge" to notEdge}")

            val countOfVisible = visibleFromOutside.size
            println("${"countOfVisible" to countOfVisible}")

            val bestTreeForScenicScore = flattenGrid.maxBy { tree ->
                tree.scenicScore
            }
            val bestScenicScore = bestTreeForScenicScore.scenicScore
            println("${"bestScenicScore" to bestScenicScore}")
        }
}