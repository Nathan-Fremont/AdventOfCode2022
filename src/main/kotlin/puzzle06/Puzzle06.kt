package puzzle06

import puzzle05.HanoiTower
import puzzle05.HanoiTowerMapper
import java.nio.file.Files
import kotlin.io.path.Path
import kotlin.io.path.exists

private const val INPUT_FILE_NAME = "inputPuzzle06"
private val signalHelper = SignalHelper()

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

            val parsedSignal = signalHelper.parseSignalReceived(
                fileContent = fileContent,
            )

            println("parsedSignal.packetMarkerValue = ${parsedSignal.packetMarkerValue}")
            println("parsedSignal.startOfPacketMarker = ${parsedSignal.startOfPacketMarker}")
            println("parsedSignal.firstMessagePacketMarkerValue = ${parsedSignal.firstMessagePacketMarkerValue}")
            println("parsedSignal.firstMessagePacketMarker = ${parsedSignal.firstMessagePacketMarker}")
        }
}