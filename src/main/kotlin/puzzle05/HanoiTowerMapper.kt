package puzzle05

import java.util.*

internal class HanoiTowerMapper() {
    fun mapFileToHanoiTower(
        fileContent: String,
    ): HanoiTower {
        println("mapFileToHanoiTower")
        return HanoiTower(
            hanoiStacks = mapFileContentToHanoiStacks(
                fileContent = fileContent,
            ),
            hanoiMoves = mapFileContentToHanoiMoves(
                fileContent = fileContent,
            )
        )
    }

    /**
     * [Q]         [N]             [N]
     * [H]     [B] [D]             [S] [M]
     * [C]     [Q] [J]         [V] [Q] [D]
     * [T]     [S] [Z] [F]     [J] [J] [W]
     * [N] [G] [T] [S] [V]     [B] [C] [C]
     * [S] [B] [R] [W] [D] [J] [Q] [R] [Q]
     * [V] [D] [W] [G] [P] [W] [N] [T] [S]
     * [B] [W] [F] [L] [M] [F] [L] [G] [J]
     *  1   2   3   4   5   6   7   8   9
     */
    private fun mapFileContentToHanoiStacks(fileContent: String): List<Stack<String>> {
        println("mapFileContentToHanoiStacks")

        val fileLines = fileContent
            .substring(
                startIndex = fileContent.indexOf(string = HANOI_STACKS_START),
                endIndex = fileContent.indexOf(string = HANOI_STACKS_END),
            )
            .trim()
            .replace("\r", "")
            .split("\n")

        val fileStacks = fileLines
            .dropLast(1)
            .reversed()
            .toList()

        val hanoiStacks = mutableListOf<Stack<String>>()

        fileLines
            .last()
            .forEachIndexed { index, c ->
                if (c.isDigit()) {
                    val newStack = Stack<String>()

                    fileStacks.forEach { palletInStack ->
                        if (palletInStack.length > index
                            && palletInStack[index].isLetter()
                        ) {
                            newStack.push(palletInStack[index].toString())
                        }
                    }
                    hanoiStacks += newStack
                }
            }

        println("mapFileContentToHanoiStacks, hanoiStacks = \n$hanoiStacks")

        return hanoiStacks
    }

    private fun mapFileContentToHanoiMoves(fileContent: String): List<HanoiMove> {
        println("mapFileContentToHanoiMoves")

        val moveLines = fileContent
            .substring(
                startIndex = fileContent.indexOf(string = HANOI_STACKS_END),
            )
            .trim()
            .replace("\r", "")
            .split("\n")

        println("mapFileContentToHanoiMoves moveLines = $moveLines")

        val moveRegex = Regex(HANOI_MOVE_REGEX)
        val hanoiMoves = moveLines
            .mapNotNull { line ->
                moveRegex
                    .find(line)
                    ?.groupValues
                    ?.run {
                        HanoiMove(
                            palletNumberToMove = this[1].toInt(),
                            fromStack = this[2].toInt(),
                            toStack = this[3].toInt(),
                        )
                    }
            }

        return hanoiMoves
    }

    companion object {
        private const val HANOI_STACKS_START = "["
        private const val HANOI_STACKS_END = "move"
        private const val HANOI_MOVE_REGEX = "move (\\d+) from (\\d+) to (\\d+)"
    }
}