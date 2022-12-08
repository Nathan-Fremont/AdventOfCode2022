package puzzle08

class TreesGridHelper {
    fun parseFileContentAsTreeGrid(fileContent: String): List<List<Tree>> {
        println("parseFileContentAsTreeGrid")
        val fileLines = fileContent
            .replace("\r", "")
            .split("\n")
            .map { line ->
                line.trim()
            }
            .filter { line ->
                line.isNotBlank()
            }

        val gridOfTrees = fileLines
            .mapIndexed { yIndex, line ->
                line
                    .mapIndexed { xIndex, char ->
                        Tree(
                            x = xIndex,
                            y = yIndex,
                            height = char.digitToInt(),
//                            isVisibleFromTop = yIndex == 0,
//                            isVisibleFromLeft = xIndex == 0,
//                            isVisibleFromRight = xIndex == fileLines.size - 1,
//                            isVisibleFromBottom = yIndex == fileLines.size - 1
                        )
                    }
                    .toMutableList()
            }
            .toMutableList()

        println("parseFileContentAsTreeGrid ${"gridOfTrees" to gridOfTrees}")

        val calculatedGridOfTrees = calculateVisibilityOfTrees(givenGridOfTrees = gridOfTrees)
        println("parseFileContentAsTreeGrid ${"calculatedGridOfTrees" to calculatedGridOfTrees}")

        return calculatedGridOfTrees
    }

    private fun calculateVisibilityOfTrees(givenGridOfTrees: MutableList<MutableList<Tree>>): MutableList<MutableList<Tree>> {
        println("calculateVisibilityOfTrees")
        val resultGridOfTrees = givenGridOfTrees.toMutableList()
        givenGridOfTrees
            .forEachIndexed { yIndex, lineOfTrees ->
                lineOfTrees
                    .forEachIndexed { xIndex, tree ->
                        // Top
                        val topColumn = givenGridOfTrees
                            .subList(
                                fromIndex = 0,
                                toIndex = yIndex
                            )
                            .map { line ->
                                line[xIndex]
                            }
                            .reversed()
                        val higherThanHowManyTop = getTreesSmallerThanGivenTree(
                            givenTree = tree,
                            listOfTrees = topColumn
                        )
                        resultGridOfTrees[yIndex][xIndex].isVisibleFromTop = higherThanHowManyTop.size == topColumn.size
                        resultGridOfTrees[yIndex][xIndex].numberOfTreesOnTop = getTreesSeenFromGivenTree(
                            givenTree = tree,
                            listOfTrees = topColumn,
                        ).size

                        // Left
                        val leftRow = lineOfTrees
                            .subList(
                                fromIndex = 0,
                                toIndex = xIndex
                            )
                            .reversed()
                        val higherThanHowManyLeft = getTreesSmallerThanGivenTree(
                            givenTree = tree,
                            listOfTrees = leftRow
                        )
                        resultGridOfTrees[yIndex][xIndex].isVisibleFromLeft = higherThanHowManyLeft.size == leftRow.size
                        resultGridOfTrees[yIndex][xIndex].numberOfTreesOnLeft = getTreesSeenFromGivenTree(
                            givenTree = tree,
                            listOfTrees = leftRow,
                        ).size

                        // Right
                        val rightRow = lineOfTrees
                            .subList(
                                fromIndex = xIndex,
                                toIndex = lineOfTrees.size,
                            )
                            .drop(1)
                        val higherThanHowManyRight = getTreesSmallerThanGivenTree(
                            givenTree = tree,
                            listOfTrees = rightRow
                        )
                        resultGridOfTrees[yIndex][xIndex].isVisibleFromRight = higherThanHowManyRight.size == rightRow.size
                        resultGridOfTrees[yIndex][xIndex].numberOfTreesOnRight = getTreesSeenFromGivenTree(
                            givenTree = tree,
                            listOfTrees = rightRow,
                        ).size

                        // Bottom
                        val bottomColumn = givenGridOfTrees
                            .subList(
                                fromIndex = yIndex,
                                toIndex = givenGridOfTrees.size,
                            )
                            .map { line ->
                                line[xIndex]
                            }
                            .drop(1)
                        val higherThanHowManyBottom = getTreesSmallerThanGivenTree(
                            givenTree = tree,
                            listOfTrees = bottomColumn
                        )
                        resultGridOfTrees[yIndex][xIndex].isVisibleFromBottom = higherThanHowManyBottom.size == bottomColumn.size
                        resultGridOfTrees[yIndex][xIndex].numberOfTreesOnBottom = getTreesSeenFromGivenTree(
                            givenTree = tree,
                            listOfTrees = bottomColumn,
                        ).size
                    }
            }
        return resultGridOfTrees
    }

    private fun getTreesSeenFromGivenTree(givenTree: Tree, listOfTrees: List<Tree>): List<Tree> {
        val smallerTrees = mutableListOf<Tree>()
        listOfTrees.forEachIndexed { _, treeInList ->
            if (givenTree.height > treeInList.height) {
                smallerTrees += treeInList
            } else {
                smallerTrees += treeInList
                return smallerTrees
            }
        }
        return smallerTrees
    }

    private fun getTreesSmallerThanGivenTree(givenTree: Tree, listOfTrees: List<Tree>): List<Tree> {
        val smallerTrees = mutableListOf<Tree>()
        listOfTrees.forEachIndexed { _, treeInList ->
            if (givenTree.height > treeInList.height) {
                smallerTrees += treeInList
            } else {
                return@forEachIndexed
            }
        }
        return smallerTrees
    }
}