fun main() {
    fun part1(input: List<String>): Int {
        return input.filter {
            it.isNotEmpty()
        }.chunked( 3 ).sumOf { machineData ->
            val buttonA = machineData.first().split( "[+,]".toRegex()).let {
                Pair( it[ 1 ].toInt(), it[ 3 ].toInt())
            }

            val buttonB = machineData[ 1 ].split( "[+,]".toRegex()).let {
                Pair( it[ 1 ].toInt(), it[ 3 ].toInt())
            }

            val prize = machineData.last().split( "[=,]".toRegex()).let {
                Pair( it[ 1 ].toInt(), it[ 3 ].toInt())
            }

            // (X*d - Y * c) / (a* d - c * b)
            val pushA = ( prize.first * buttonB.second
                    - prize.second * buttonB.first ) / ( buttonA.first
                    * buttonB.second - buttonB.first * buttonA.second )
            // (Y * a - X * b) / ( d * a - b * c )
            val pushB = ( prize.second * buttonA.first
                    - prize.first * buttonA.second ) / ( buttonB.second
                    * buttonA.first - buttonA.second * buttonB.first )

            if ( pushA <= 100 && pushB <= 100 && pushA * buttonA.first
                + pushB * buttonB.first == prize.first
                && pushA * buttonA.second + pushB * buttonB.second
                == prize.second ) pushA * 3 + pushB else 0
        }

//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Long {
        val prizeAdd = 10000000000000

        return input.filter {
            it.isNotEmpty()
        }.chunked( 3 ).sumOf { machineData ->
            val buttonA = machineData.first().split( "[+,]".toRegex()).let {
                Pair( it[ 1 ].toInt(), it[ 3 ].toInt())
            }

            val buttonB = machineData[ 1 ].split( "[+,]".toRegex()).let {
                Pair( it[ 1 ].toInt(), it[ 3 ].toInt())
            }

            val prize = machineData.last().split( "[=,]".toRegex()).let {
                Pair( prizeAdd + it[ 1 ].toInt(), prizeAdd + it[ 3 ].toInt())
            }

            // (X*d - Y * c) / (a* d - c * b)
            val pushA = ( prize.first * buttonB.second
                    - prize.second * buttonB.first ) / ( buttonA.first
                    * buttonB.second - buttonB.first * buttonA.second )
            // (Y * a - X * b) / ( d * a - b * c )
            val pushB = ( prize.second * buttonA.first
                    - prize.first * buttonA.second ) / ( buttonB.second
                    * buttonA.first - buttonA.second * buttonB.first )

            if ( pushA * buttonA.first + pushB * buttonB.first == prize.first
                && pushA * buttonA.second + pushB * buttonB.second
                == prize.second ) pushA * 3 + pushB else 0
        }


//        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day13")
    part1(input).println()
    part2(input).println()
}
