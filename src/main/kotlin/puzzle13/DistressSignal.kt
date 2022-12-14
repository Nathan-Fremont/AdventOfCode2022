package puzzle13

sealed class DistressSignal {
    abstract val stringValue: String
    abstract val isDecoderKey: Boolean

    data class Single(
        override val stringValue: String,
        override val isDecoderKey: Boolean,
        val value: Int
    ) : DistressSignal()
    data class Multiple(
        override val stringValue: String,
        override val isDecoderKey: Boolean,
        val value: List<DistressSignal>
    ) : DistressSignal()
}