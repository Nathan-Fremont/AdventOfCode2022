package puzzle08

data class BooleanWrapper(var field: Boolean)
data class IntWrapper(var field: Int)

data class Tree(
    val x: Int,
    val y: Int,
    val height: Int,
    var isVisibleFromTop: BooleanWrapper = BooleanWrapper(field = false),
    var isVisibleFromLeft: BooleanWrapper = BooleanWrapper(field = false),
    var isVisibleFromRight: BooleanWrapper = BooleanWrapper(field = false),
    var isVisibleFromBottom: BooleanWrapper = BooleanWrapper(field = false),
    var numberOfTreesOnTop: IntWrapper = IntWrapper(field = 0),
    var numberOfTreesOnLeft: IntWrapper = IntWrapper(field = 0),
    var numberOfTreesOnRight: IntWrapper = IntWrapper(field = 0),
    var numberOfTreesOnBottom: IntWrapper = IntWrapper(field = 0),
    var calculatedScenicScore: Int = 0,
) {
    fun isVisibleFromOutside() = isVisibleFromTop.field
            || isVisibleFromLeft.field
            || isVisibleFromRight.field
            || isVisibleFromBottom.field

    val scenicScore: Int
        get() {
            val newValue =
                numberOfTreesOnTop.field * numberOfTreesOnLeft.field * numberOfTreesOnRight.field * numberOfTreesOnBottom.field
            calculatedScenicScore = newValue
            return newValue
        }
}