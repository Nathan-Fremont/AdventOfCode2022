package puzzle04

internal sealed class SectionIntersection {
    sealed class FullIntersection : SectionIntersection() {
        object LeftFullyContainsRight : FullIntersection()
        object RightFullyContainsLeft : FullIntersection()
    }

    object PartialOverlap : SectionIntersection()
    object NoIntersection : SectionIntersection()
}