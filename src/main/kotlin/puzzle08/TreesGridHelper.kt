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
                        getInfosForTree(
                            givenTree = tree,
                            listOfTrees = topColumn,
                            treeIsVisible = resultGridOfTrees[yIndex][xIndex].isVisibleFromTop,
                            numberOfTreesInDirection = resultGridOfTrees[yIndex][xIndex].numberOfTreesOnTop,
                        )

                        // Left
                        val leftRow = lineOfTrees
                            .subList(
                                fromIndex = 0,
                                toIndex = xIndex
                            )
                            .reversed()
                        getInfosForTree(
                            givenTree = tree,
                            listOfTrees = leftRow,
                            treeIsVisible = resultGridOfTrees[yIndex][xIndex].isVisibleFromLeft,
                            numberOfTreesInDirection = resultGridOfTrees[yIndex][xIndex].numberOfTreesOnLeft,
                        )

                        // Right
                        val rightRow = lineOfTrees
                            .subList(
                                fromIndex = xIndex,
                                toIndex = lineOfTrees.size,
                            )
                            .drop(1)
                        getInfosForTree(
                            givenTree = tree,
                            listOfTrees = rightRow,
                            treeIsVisible = resultGridOfTrees[yIndex][xIndex].isVisibleFromRight,
                            numberOfTreesInDirection = resultGridOfTrees[yIndex][xIndex].numberOfTreesOnRight,
                        )

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
                        getInfosForTree(
                            givenTree = tree,
                            listOfTrees = bottomColumn,
                            treeIsVisible = resultGridOfTrees[yIndex][xIndex].isVisibleFromBottom,
                            numberOfTreesInDirection = resultGridOfTrees[yIndex][xIndex].numberOfTreesOnBottom,
                        )
                    }
            }
        return resultGridOfTrees
    }

    private fun getInfosForTree(
        givenTree: Tree,
        listOfTrees: List<Tree>,
        treeIsVisible: BooleanWrapper,
        numberOfTreesInDirection: IntWrapper,
    ) {
        val seenFromGivenTree = getTreesSeenFromGivenTree(
            givenTree = givenTree,
            listOfTrees = listOfTrees,
        )
        numberOfTreesInDirection.field = seenFromGivenTree.size
        treeIsVisible.field = seenFromGivenTree.filter { tree ->
            givenTree.height > tree.height
        }.size == listOfTrees.size
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
}