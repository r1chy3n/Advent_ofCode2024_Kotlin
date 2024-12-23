fun main() {
    fun part1(input: List<String>): Int {
        val patternList = input.first().split( ", " ).sortedByDescending {
            it.length
        } // List<String>.sortedByDescending

        val cache = mutableMapOf<String, Boolean>()

        fun recognize( designIndex: Int, startIndex: Int ): Boolean {
            var result = false
            val subDesign = input[ designIndex ].substring( startIndex )

            if ( subDesign.isEmpty()) {
                result = true
            } else if ( cache.contains( subDesign )) {
                result = cache.getValue( subDesign )
            } else {
                result = patternList.filter { pattern ->
                    subDesign.startsWith( pattern )
                }.any { pattern ->
                    recognize( designIndex, startIndex + pattern.length )
                } // List<String>.any

                cache.put( subDesign, result )
            } // if - else_if - else

            return result
        } // fun recognize( Int, Int )

        return ( 2 .. input.lastIndex ).count { designIndex ->
            recognize( designIndex, 0 )
        } // IntRange.count

//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Long {
        val patternList = input.first().split( ", " ).sortedByDescending {
            it.length
        } // List<String>.sortedByDescending

        val cache = mutableMapOf<String, Long>()

        fun recognize( designIndex: Int, startIndex: Int ): Long {
            var result = 0L
            val subDesign = input[ designIndex ].substring( startIndex )

            if ( subDesign.isEmpty()) {
                result = 1
            } else if ( cache.contains( subDesign )) {
                result = cache.getValue( subDesign )
            } else {
                result = patternList.filter { pattern ->
                    subDesign.startsWith( pattern )
                }.sumOf { pattern ->
                    recognize( designIndex, startIndex + pattern.length )
                } // List<String>.sumOf

                cache.put( subDesign, result )
            } // if - else_if - else

            return result
        } // fun recognize( Int, Int )

        return ( 2 .. input.lastIndex ).sumOf { designIndex ->
            recognize( designIndex, 0 )
        } // IntRange.sumOf

//        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day19")
    part1(input).println()
    part2(input).println()
}
