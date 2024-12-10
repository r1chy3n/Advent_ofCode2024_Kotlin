import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        var headIndex = 0
        var trailIndex = input.first().lastIndex
        var spaceCount: Int
        var trailCount: Int
        var fileRemain = 0
        var spaceRemain = 0
        var pos = 0
        var sum = 0L

        do {
            if (headIndex % 2 == 0) {
                repeat(input.first()[headIndex].digitToInt()) {
                    sum += headIndex / 2 * pos++
                } // repeat
            } else {
                do {
                    spaceCount = if (spaceRemain > 0)
                        spaceRemain else input.first()[headIndex].digitToInt()
                    trailCount = if (fileRemain > 0)
                        fileRemain else input.first()[trailIndex].digitToInt()

                    repeat(min(spaceCount, trailCount)) { _ ->
                        sum += trailIndex / 2 * pos++
                    } // repeat

                    if (trailCount < spaceCount) {
                        spaceRemain = spaceCount - trailCount
                        fileRemain = 0
                        trailIndex -= 2
                    } else if (spaceCount < trailCount) {
                        spaceRemain = 0
                        fileRemain = trailCount - spaceCount
                    } else {
                        spaceRemain = 0
                        fileRemain = 0
                    } // if - else if - else
                } while (spaceRemain > 0)

                if (fileRemain == 0) {
                    trailIndex -= 2
                } // if
            } // if - else

            headIndex++
        } while ( headIndex < trailIndex )

        if ( fileRemain > 0 ) {
            repeat( fileRemain ) { _ ->
                sum += trailIndex / 2 * pos++
            } // repeat
        } // if

        return sum
    } // fun part1( List<String>)

    fun blockPos( diskMap: String, endIndex: Int ): Int {
        return diskMap.substring( 0, endIndex ).sumOf { ch ->
            ch.digitToInt()
        } // return: String.substring.sumOf
    } // fun blockPos

    fun part2(input: List<String>): Long {
        val diskMap = input.first()
        val spaceList = diskMap.mapIndexed { index, ch ->
            Triple( index, ch.digitToInt(), blockPos( diskMap, index ))
        }.filter { triple ->
            triple.first % 2 == 1
        }.toMutableList()

        var sum = 0L
        var trailIndex = diskMap.lastIndex

        var fileSize: Int
        var refIndex: Int
        var spaceSize: Int
        var blockPos: Int
        var spaceInfo: Triple<Int, Int, Int>

        do {
            fileSize = diskMap[ trailIndex ].digitToInt()
            refIndex = spaceList.indexOfFirst { pair ->
                pair.second >= fileSize
            }

            if ( refIndex == -1 ) {
                blockPos = diskMap.substring( 0, trailIndex ).sumOf { ch ->
                    ch.digitToInt()
                }

                repeat( fileSize ) {
                    sum += trailIndex / 2 * blockPos++
                }
            } else {
                spaceInfo = spaceList[ refIndex ]
                blockPos = spaceInfo.third

                repeat( fileSize ) {
                    sum += trailIndex / 2 * blockPos++
                }

                spaceSize = spaceInfo.second

                spaceList.removeAt( refIndex )

                if ( fileSize < spaceSize ) {
                    spaceList.add( refIndex, Triple( spaceInfo.first,
                        spaceSize - fileSize, spaceInfo.third + fileSize ))
                }
            } // if - else

            trailIndex -= 2

            spaceList.removeIf { t -> t.first >= trailIndex }
        } while ( trailIndex > 0 )

        return sum
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
