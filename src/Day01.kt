import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val leftList = mutableListOf<Int>()
        val rightList = mutableListOf<Int>()

        input.forEach { line ->
            line.split("\\s+".toRegex()).let { splitList ->
                leftList.add(splitList.first().toInt())
                rightList.add(splitList.last().toInt())
            } // let
        } // forEach

        leftList.sort()
        rightList.sort()

        return leftList.mapIndexed { i, left ->
            ( rightList[ i ] - left ).absoluteValue
        }.sum()
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        val leftList = mutableListOf<String>()
        val rightList = mutableListOf<String>()

        input.forEach { line ->
            line.split( "\\s+".toRegex()).let { splitList ->
                leftList.add( splitList.first())
                rightList.add( splitList.last())
            } // let
        } // forEachLine

        return leftList.mapIndexed { i, left ->
            left.toInt() * rightList.count { right ->
                left == right
            } // count
        }.sum()
    } // fun part2( List<String>)

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
//    val testInput = readInput("Day01_test")
//    check(part1(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
