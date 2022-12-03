package puzzle03

import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

private const val INPUT_FILE_NAME = "inputPuzzle03"
private const val LINE_SEPARATOR = "\n"

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
            val bagsList = getListOfBags(
                fileContent
            )

            val bagsWithValues = calculateForEachBag(
                bagsList = bagsList
            )

            val totalForAllBags = bagsWithValues
                .sumOf { bagWithValue ->
                    val totalForBag = bagWithValue.duplicatedChars.sumOf { duplicatedChar ->
                        duplicatedChar.calculatedValue()
                    }
                    println("totalForBag = $totalForBag")
                    totalForBag
                }
            println("totalForAllBags = $totalForAllBags")

            val elfsGroups = bagsWithValues
                .chunked(3)
                .map { bags ->
                    val elfGroupLines = bags
                        .map { bagWithValue ->
                            bagWithValue.line
                        }

                    var intersected = elfGroupLines[0]
                    elfGroupLines.forEach {
                        intersected = (intersected.toCharArray()
                            .intersect(it.toCharArray().asIterable().toSet())).toString()
                    }
                    val badgeForGroup = intersected
                        .replace("[", "")
                        .replace("]", "")
                        .first()
                    println("badgeForGroup = $badgeForGroup")
                    ElfGroup(
                        bags = bags,
                        badge = badgeForGroup,
                    )
                }
            val totalForAllElfGroups = elfsGroups.sumOf { elfGroup ->
                elfGroup.badge.calculatedValue()
            }
            println("totalForAllElfGroups = $totalForAllElfGroups")

        } ?: println("Error $INPUT_FILE_NAME does not exists")
}

private fun getListOfBags(fileContent: String, splitBy: Int = 2): List<Bag> {
    println("getListOfBags")
    return fileContent
        .split(LINE_SEPARATOR)
        .filter { line ->
            line.isNotBlank()
        }
        .map { line ->
            line.replace("\r", "")
        }
        .map { line ->
            println("line = $line")
            val chunks = line.chunked(
                size = line.length / splitBy,
            )
            val newBag = Bag(
                line = line,
                containers = chunks
            )
            newBag
        }
}

private fun calculateForEachBag(bagsList: List<Bag>): List<BagWithValue> {
    println("calculateForEachBag")

    return bagsList
        .map { bag ->
            val duplicatedChars = bag
                .containers
                .zipWithNext()
                .flatMap { pair ->
                    pair.first.toCharArray()
                        .intersect(pair.second.toCharArray().asIterable().toSet())
                }
            println("duplicatedChars $duplicatedChars")
            BagWithValue(
                line = bag.line,
                containers = bag.containers,
                duplicatedChars = duplicatedChars,
                valuesForContainers = bag.containers.map { container ->
                    calculateValueForContainer(container)
                }
            )
        }
}

private fun calculateValueForContainer(container: String): Map<Char, Int> {
    val bagMap = mutableMapOf<Char, Int>()
    container
        .toCharArray()
        .distinct()
        .forEach { char ->
            val charValue = char.calculatedValue()
            val charCount = container.count { itChar ->
                itChar == char
            }
            bagMap[char] = charValue// * charCount
        }
    println("calculateValueForContainer $bagMap")

    return bagMap
}

private fun Char.calculatedValue(): Int = if (this.isLowerCase()) {
    (this - 'a') + 1
} else {
    (this - 'A') + 27
}