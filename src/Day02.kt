fun main() {
    fun part1(input: List<String>): Int {
        return input.count { line ->
            val levelPairs = line.split( " " ).map { level ->
                level.toInt()
            }.zipWithNext()

            levelPairs.all {( a, b ) ->
                a < b && b - a in 1..3
            } || levelPairs.all {( a, b ) ->
                a > b && a - b in 1..3
            } // all
        } // count
    } // fun part1( List<String>)

    fun isSafe( levelList: List<Int>) = levelList.zipWithNext().let { pairs ->
        pairs.all {( a, b ) ->
            a < b && b - a in 1..3
        } || pairs.all {( a, b ) ->
            a > b && a - b in 1..3
        } // pairs - all
    } // fun isSafe( List<Int>) - let

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val levelList = line.split( " " ).map { level ->
                level.toInt()
            } // map

            isSafe( levelList ) || ( 0..levelList.lastIndex ).any { i ->
                isSafe(
                    levelList.subList( 0, i ) +
                            levelList.subList( i + 1, levelList.size )
                ) // isSafe
            } // Range - any
        } // count
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
