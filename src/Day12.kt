fun main() {
    fun part1(input: List<String>): Int {
        val region2D = input.map { line ->
            line.map {
                0
            }.toMutableList()
        } // List<String>.map

        val perimeterMap = mutableMapOf<Int, Int>()

        var id = 0
        var prev: Char?
        var next: Char?
        var above: Char?
        var below: Char?
        var periCount: Int

        input.indices.forEach { y ->
            input[y].indices.forEach { x ->
                periCount = 0
                prev = input[y].getOrNull(x - 1)
                next = input[y].getOrNull(x + 1)
                above = input.getOrNull(y - 1)?.get(x)
                below = input.getOrNull(y + 1)?.get(x)

                if (prev == null || prev != input[y][x]) periCount++
                if (next == null || next != input[y][x]) periCount++
                if (above == null || above != input[y][x]) periCount++
                if (below == null || below != input[y][x]) periCount++

                // 如果前面與上面的字元皆為空，則使用新的識別碼
                region2D[y][x] = if (prev == null)
                    (if (above == null) ++id
                    // 如果前面字元為空而與上面字元一樣，則使用與上面字元相同的識別碼
                    else (if (input[y][x] == above)
                        region2D[y - 1][x]
                    // 如果前面字元為空而與上面字元不同，則使用新的識別碼
                    else ++id))
                // 如果與前面字元一樣，則使用與前面字元相同的識別碼
                else (if (input[y][x] == prev)
                    region2D[y][x - 1].also { currId ->
                        above?.let {
                            val aboveId = region2D[y - 1][x]

                            if (aboveId != currId
                                && input[ y ][ x ] == above ) {
                                region2D.indices.forEach { y2 ->
                                    region2D[y2].indices.forEach { x2 ->
                                        if (region2D[y2][x2]
                                            == aboveId
                                        ) {
                                            region2D[y2][x2] = currId
                                        } // if
                                    } // IntRange.forEach
                                } // IntRange.forEach

                                perimeterMap.put(
                                    currId, perimeterMap.getValue( currId )
                                            + perimeterMap.getValue( aboveId )
                                )
                            } // if
                        } // Char?.let
                    } // Int.also
                // 如果與前面字元不同，而上面字元為空，則使用新的識別碼
                else (if (above == null) ++id
                // 如果與前面字元不同，而與上面字元一樣，則使用與上面字元相同的識別碼
                else (if (input[y][x] == above)
                    region2D[y - 1][x]
                // 如果與前面以及上面字元皆不同，則使用新的識別碼
                else ++id)))

                perimeterMap.put( region2D[y][x],
                    perimeterMap.getOrDefault( region2D[y][x], 0 ) + periCount )
            } // IntRange.forEach
        } // IntRange.forEach

        var sum = 0

        region2D.flatMap { it }.groupingBy {
            it
        }.eachCount().forEach {( id, count ) ->
            sum += perimeterMap.getValue( id ) * count
        }

        return sum

//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        val region2D = input.map { line ->
            line.map {
                0
            }.toMutableList()
        } // List<String>.map

        val perimeterMap =
            mutableMapOf<Int, MutableList<Triple<Int, Int, Int>>>()

        var id = 0
        var prev: Char?
        var next: Char?
        var above: Char?
        var below: Char?

        input.indices.forEach { y ->
            input[y].indices.forEach { x ->
                prev = input[y].getOrNull(x - 1)
                next = input[y].getOrNull(x + 1)
                above = input.getOrNull(y - 1)?.get(x)
                below = input.getOrNull(y + 1)?.get(x)

                // 如果前面與上面的字元皆為空，則使用新的識別碼
                region2D[y][x] = if (prev == null)
                    (if (above == null) ++id
                    // 如果前面字元為空而與上面字元一樣，則使用與上面字元相同的識別碼
                    else (if (input[y][x] == above)
                        region2D[y - 1][x]
                    // 如果前面字元為空而與上面字元不同，則使用新的識別碼
                    else ++id))
                // 如果與前面字元一樣，則使用與前面字元相同的識別碼
                else (if (input[y][x] == prev)
                    region2D[y][x - 1].also { currId ->
                        above?.let {
                            val aboveId = region2D[y - 1][x]

                            if (aboveId != currId
                                && input[ y ][ x ] == above ) {

                                perimeterMap.remove( aboveId )?.let {
                                    perimeterMap.getValue( currId ).addAll( it )
                                }

                                region2D.indices.forEach { y2 ->
                                    region2D[y2].indices.forEach { x2 ->
                                        if (region2D[y2][x2]
                                            == aboveId
                                        ) {
                                            region2D[y2][x2] = currId
                                        } // if
                                    } // IntRange.forEach
                                } // IntRange.forEach
                            } // if
                        } // Char?.let
                    } // Int.also
                // 如果與前面字元不同，而上面字元為空，則使用新的識別碼
                else (if (above == null) ++id
                // 如果與前面字元不同，而與上面字元一樣，則使用與上面字元相同的識別碼
                else (if (input[y][x] == above)
                    region2D[y - 1][x]
                // 如果與前面以及上面字元皆不同，則使用新的識別碼
                else ++id)))

                val perimeterList = perimeterMap.getOrPut( region2D[ y ][ x ]) {
                    mutableListOf()
                } //

                // 右：0，下：1，左：2，上：3
                if (prev == null || prev != input[y][x]) {
                    perimeterList.remove( Triple( y - 1, x, 2 ))
                    perimeterList.add(Triple( y, x, 2 ))
                } // if

                if (next == null || next != input[y][x]) {
                    perimeterList.remove( Triple( y - 1, x, 0 ))
                    perimeterList.add(Triple( y, x, 0 ))
                } // if

                if (above == null || above != input[y][x]) {
                    perimeterList.remove( Triple( y, x - 1, 3 ))
                    perimeterList.add(Triple( y, x, 3 ))
                } // if

                if (below == null || below != input[y][x]) {
                    perimeterList.remove( Triple( y, x - 1, 1 ))
                    perimeterList.add(Triple( y, x, 1 ))
                } // if
            } // IntRange.forEach
        } // IntRange.forEach

        var sum = 0

        region2D.flatMap { it }.groupingBy {
            it
        }.eachCount().forEach {( id, count ) ->
            sum += perimeterMap.getValue( id ).size * count
        }

        return sum
//        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day12")
    part1(input).println()
    part2(input).println()
}
