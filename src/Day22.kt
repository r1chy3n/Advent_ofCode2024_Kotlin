fun main() {
//    fun part1(input: List<String>): Int {
    fun part1(input: List<String>): Long {
        var initSecret: Long
        var secret = 0L
        var secretX64: Long
        var dividingBy32: Long
        var secretX2048: Long

        return input.sumOf { line ->
            initSecret = line.toLong()

            repeat( 2000 ) {
                secret = initSecret
                // Step 1
                // Calculate the result of multiplying the secret number by 64.
                secretX64 = secret * 64
                // Then, mix this result into the secret number.
                secret = secret xor secretX64
                // Finally, prune the secret number.
                secret = secret % 16777216

                // Step 2
                // Calculate the result of dividing the secret number by 32.
                // Round the result down to the nearest integer.
                dividingBy32 = secret / 32
                // Then, mix this result into the secret number.
                secret = secret xor dividingBy32
                // Finally, prune the secret number.
                secret = secret % 16777216
                // Step 3
                // Calculate the result of multiplying the secret number by 2048.
                secretX2048 = secret * 2048
                // Then, mix this result into the secret number.
                secret = secret xor secretX2048
                // Finally, prune the secret number.
                secret = secret % 16777216
                initSecret = secret
            } // repeat

            secret
        } // List<String>.sumOf

//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        var initSecret: Long
        var secret = 0L
        var secretX64: Long
        var dividingBy32: Long
        var secretX2048: Long
        var prePrice: Int
        var price: Int
        var firstC4Index: Int

        val c4PriceLists = input.map { line ->
            mutableListOf<Pair<Int, Int>>().also { priceChanged ->
                initSecret = line.toLong()

                repeat( 2000 ) {
                    prePrice = initSecret.mod( 10 )
                    secret = initSecret
                    // Step 1
                    // Calculate the result of multiplying the secret number by 64.
                    secretX64 = secret * 64
                    // Then, mix this result into the secret number.
                    secret = secret xor secretX64
                    // Finally, prune the secret number.
                    secret = secret % 16777216

                    // Step 2
                    // Calculate the result of dividing the secret number by 32.
                    // Round the result down to the nearest integer.
                    dividingBy32 = secret / 32
                    // Then, mix this result into the secret number.
                    secret = secret xor dividingBy32
                    // Finally, prune the secret number.
                    secret = secret % 16777216
                    // Step 3
                    // Calculate the result of multiplying the secret number by 2048.
                    secretX2048 = secret * 2048
                    // Then, mix this result into the secret number.
                    secret = secret xor secretX2048
                    // Finally, prune the secret number.
                    secret = secret % 16777216
                    price = secret.mod( 10 )

                    priceChanged.add( price to price - prePrice )

                    initSecret = secret
                } // repeat
            }.windowed( 4, 1 ).map { windowed ->
                windowed.map { e ->
                    e.second
                } to windowed.last().first
            } // List<List<Pair<Int, Int>>>.map
        }

        val c4ListCountMap = c4PriceLists.flatMap { c4PriceList ->
            c4PriceList.map {( c4List, _) ->
                c4List
            } // List<Pair<List<Int>, Int>>.map
        }.groupingBy { c4List ->
            c4List
        }.eachCount().toMutableMap()

        val maxCount = c4ListCountMap.maxOf {(_, count ) ->
            count
        } // MutableMap<List<Int>, Int>.maxOf

        c4ListCountMap.keys.toMutableList().forEach { c4List ->
            if ( c4ListCountMap.getValue( c4List ) < maxCount / 2 ) {
                c4ListCountMap.remove( c4List )
            } // if
        } //  MutableList<List<Int>>.forEach

        return c4ListCountMap.maxOf {( consecutive4, _) ->
            c4PriceLists.sumOf { c4PriceList ->
                firstC4Index = c4PriceList.indexOfFirst {( c4List, _ ) ->
                    c4List == consecutive4
                } // List<Pair<List<Int>, Int>>.indexOfFirst

                if ( firstC4Index == -1 ) 0
                else c4PriceList[ firstC4Index ].second
            } // List<List<Pair<List<Int>, Int>>>.sumOf
        } // MutableMap<List<Int>, Int>.maxOf

//        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day22")
    part1(input).println()
    part2(input).println()
}
