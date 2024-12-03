fun main() {
    fun findAndCal( line: String ) = "mul\\(\\d{1,3}?,\\d{1,3}?\\)"
        .toRegex().findAll( line ).sumOf { result ->
        "\\d+".toRegex().findAll(
            result.value
        ).zipWithNext().sumOf { pair ->
            pair.first.value.toInt() * pair.second.value.toInt()
        } // sumOf
    } // sumOf

    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            findAndCal( line )
        } // sumOf
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        return input.joinToString().split( "do()" ).sumOf { string ->
            findAndCal( string.split( "don't()" ).first())
        } // sumOf
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
