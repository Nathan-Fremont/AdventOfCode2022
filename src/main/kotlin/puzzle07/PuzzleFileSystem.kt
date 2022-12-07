package puzzle07

import java.math.BigInteger

sealed class PuzzleFileSystem {
    abstract val name: String
    abstract val size: BigInteger

    data class PuzzleDirectory(
        override val name: String,
        var files: MutableList<PuzzleFileSystem>,
    ) : PuzzleFileSystem() {
        private var calculatedSize: BigInteger? = null

        //        override val size: BigInteger
//            get() = if (calculatedSize != null) {
//                calculatedSize!!
//            } else {
//                calculatedSize = files.sumOf { puzzleFile ->
//                    puzzleFile.size
//                }
//                calculatedSize!!
//            }
        override val size: BigInteger
            get() {
                calculatedSize = files.sumOf { puzzleFile ->
                    puzzleFile.size
                }
                return calculatedSize!!
            }
    }

    internal data class PuzzleFile(
        override val name: String,
        override val size: BigInteger,
    ) : PuzzleFileSystem()
}