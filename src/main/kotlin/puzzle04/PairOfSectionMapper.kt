package puzzle04

import java.math.BigDecimal
import java.math.BigInteger

class PairOfSectionMapper {
    internal fun parseFileContentToPairOfSections(fileContent: String): List<PairOfSection> {
        val lines = fileContent
            .replace("\r", "")
            .split(LINE_SEPARATOR)
            .filter { line ->
                line.isNotBlank()
            }

//        println("parseFileContentToPairOfSections lines = [$lines]")
        val pairsOfSections = lines
            .map { line ->
                val (first, second) = line
                    .split(PAIR_SEPARATOR)
                PairOfSection(
                    lineRead = line,
                    leftSection = parseStringToSection(sectionAsString = first),
                    rightSection = parseStringToSection(sectionAsString = second),
                )
            }
//        println("parseFileContentToPairOfSections pairsOfSections = [$pairsOfSections]")
        return pairsOfSections
    }

    private fun parseStringToSection(sectionAsString: String): Section {
        val (lowValueAsString, highValueAsString) = sectionAsString
            .split(SECTION_SEPARATOR)

        println("parseStringToSection[$sectionAsString]")
        val section = Section(
            lowValue = BigInteger(lowValueAsString),
            highValue = BigInteger(highValueAsString),
        )
        println("parseStringToSection[$sectionAsString] = $section")
        return section
    }

    companion object {
        private const val LINE_SEPARATOR = "\n"
        private const val PAIR_SEPARATOR = ","
        private const val SECTION_SEPARATOR = "-"
    }
}