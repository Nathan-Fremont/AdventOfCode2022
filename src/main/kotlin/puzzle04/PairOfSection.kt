package puzzle04

import java.math.BigInteger

internal data class PairOfSection(
    val lineRead: String,
    val leftSection: Section,
    val rightSection: Section,
) {
    private val leftSequence = generateSequence(leftSection.lowValue) { seqValue ->
        if (seqValue < leftSection.highValue) {
            seqValue + BigInteger.ONE
        } else {
            null
        }
    }
    private val rightSequence = generateSequence(rightSection.lowValue) { seqValue ->
        if (seqValue < rightSection.highValue) {
            seqValue + BigInteger.ONE
        } else {
            null
        }
    }


    fun getSectionIntersection(): SectionIntersection = when {
        leftSection.lowValue <= rightSection.lowValue
                && leftSection.highValue >= rightSection.highValue -> {
            SectionIntersection.FullIntersection.LeftFullyContainsRight
        }

        rightSection.lowValue <= leftSection.lowValue
                && rightSection.highValue >= leftSection.highValue -> {
            SectionIntersection.FullIntersection.RightFullyContainsLeft
        }

        leftSequence.toList().intersect(rightSequence.asIterable().toSet()).isNotEmpty() -> {
            SectionIntersection.PartialOverlap
        }

        else -> {
            SectionIntersection.NoIntersection
        }
    }
}

