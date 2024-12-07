import kotlin.math.pow

fun main() {
    fun equals1( numbers: List<Long>, opCount: Int ): Boolean {
        val opUpper = 2.toFloat().pow( opCount ).toInt() - 1

        return ( 0 .. opUpper ).any { opCombine ->
            var value = numbers[ 1 ]
            var opLeft = opCombine

            repeat( opCount ) { opOrder ->
                if ( opLeft and 1 == 0 ) {
                    value += numbers[ opOrder + 2 ]
                } else {
                    value *= numbers[ opOrder + 2 ]
                } // if - else

                opLeft = opLeft shr 1
            } // repeat

            value == numbers[ 0 ]
        } // return
    } // fun equals( List<Long>)

    fun part1(input: List<String>): Long {
        return input.sumOf { line ->
            val numbers = line.split( ": | ".toRegex()).map { str ->
                str.toLong()
            } // numbers = List<String>.split.map

            val opCount = numbers.lastIndex - 1

            if ( equals1( numbers, opCount )) numbers[ 0 ] else 0
        } // List<String>.sumOf
    } // fun part1( List<String>)

    fun equals2( numbers: List<Long>, opCount: Int ): Boolean {
        val opUpper = 3.toFloat().pow( opCount ).toLong() - 1

        return ( 0 .. opUpper ).any { opCombine ->
            var value = numbers[ 1 ]
            var opLeft = opCombine

            repeat( opCount ) { opOrder ->
                val operator = opLeft % 3

                when ( operator ) {
                    0L -> value += numbers[ opOrder + 2 ]
                    1L -> value *= numbers[ opOrder + 2 ]
                    2L -> value = (
                            value.toString() + numbers[ opOrder + 2 ]
                            ).toLong()
                } // when

                opLeft /= 3
            } // repeat

            value == numbers[ 0 ]
        } // return
    } // fun equals( List<Long>)

    fun part2(input: List<String>): Long {
        return input.sumOf { line ->
            val numbers = line.split( ": | ".toRegex()).map { str ->
                str.toLong()
            } // numbers = List<String>.split.map

            val opCount = numbers.lastIndex - 1

            if ( equals1( numbers, opCount ) || equals2( numbers, opCount ))
                numbers[ 0 ] else 0
        } // List<String>.sumOf
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day07")
    part1(input).println()
    part2(input).println()
}
