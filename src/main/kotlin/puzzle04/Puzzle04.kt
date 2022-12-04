package puzzle04

import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

private const val INPUT_FILE_NAME = "inputPuzzle04"
private val mapper = PairOfSectionMapper()

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
            val pairsOfSections = mapper.parseFileContentToPairOfSections(
                fileContent = fileContent,
            )

            val intersected = pairsOfSections
                .mapIndexed { index, pairOfSection ->
                    index to pairOfSection.getSectionIntersection()
                }

            val fullIntersected = intersected
                .filter { pair ->
                    pair.second is SectionIntersection.FullIntersection
                }

//            println("fullIntersected = $fullIntersected")
            println("fullIntersectedCount = ${fullIntersected.count()}")

            val partialOverlap = intersected
                .filter { pair ->
                    pair.second is SectionIntersection.PartialOverlap
                }

//            println("partialOverlap = partialOverlap")
            println("partialOverlapCount = ${partialOverlap.count()}")

            val totalCount = fullIntersected.count() + partialOverlap.count()
            println("TotalCount = $totalCount")
        }
}