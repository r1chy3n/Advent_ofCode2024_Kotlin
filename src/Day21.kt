fun main() {
    val a = "A"
    val da = "vA"
    val dda = "vvA"
    val ddda = "vvvA"
    val ddra = "vv>A"
    val dla = "v<A"
    val dra = "v>A"
    val drra = "v>>A"
    val la = "<A"
    val lda = "<vA"
    val ldda = "<vvA"
    val lla = "<<A"
    val llda = "<<vA"
    val llua = "<<^A"
    val lua = "<^A"
    val luua = "<^^A"
    val ra = ">A"
    val rda = ">vA"
    val rdda = ">vvA"
    val rddda = ">vvvA"
    val rra = ">>A"
    val rrda = ">>vA"
    val rrua = ">>^A"
    val rua = ">^A"
    val ruua = ">^^A"
    val ua = "^A"
    val uua = "^^A"
    val uuua = "^^^A"

//        +---+---+---+
//        | 7 | 8 | 9 |
//        +---+---+---+
//        | 4 | 5 | 6 |
//        +---+---+---+
//        | 1 | 2 | 3 |
//        +---+---+---+
//            | 0 | A |
//            +---+---+
    val numericMoves = mapOf(
        // A ~ X
        'A' to '0' to la,
        'A' to '1' to "^<<A", // "^<<A" > "<^<A" 變大 130064398413965; 改回 "^<<A"
        'A' to '2' to "^<A", // lua > "^<A"; 沒變
        'A' to '3' to ua,
        'A' to '4' to "^^<<A", // "^^<<A" > "^<<^A" 變大；"^^<<A" > "<^^<A" 變大；改回 "^^<<A"
        'A' to '5' to luua, // luua > "^^<A" 沒變
        'A' to '6' to uua,
        'A' to '7' to "^^^<<A",
        'A' to '8' to "<^^^A",
        'A' to '9' to uuua,
        'A' to 'A' to a,

        // 0 ~ X
        '0' to '0' to a,
        '0' to '1' to "^<A",
        '0' to '2' to ua,
        '0' to '3' to rua, // rua > "^>A": 沒變
        '0' to '4' to "^^<A",
        '0' to '5' to uua,
        '0' to '6' to ruua, // ruua > "^^>A": 沒變；改回 ruua
        '0' to '7' to "^^^<A",
        '0' to '8' to uuua,
        '0' to '9' to "^^^>A",
        '0' to 'A' to ra,

        // 1 ~ X
        '1' to '0' to rda,
        '1' to '1' to a,
        '1' to '2' to ra,
        '1' to '3' to rra,
        '1' to '4' to ua,
        '1' to '5' to rua, // rua > "^>A": 沒變；改回 rua
        '1' to '6' to rrua, // rrua > "^>>A": 沒變；改回 rrua
        '1' to '7' to uua,
        '1' to '8' to ruua, // ruua > "^^>A": 沒變；改回 ruua
        '1' to '9' to "^^>>A", // "^^>>A" > ">>^^A"：沒變；改回 "^^>>A"
        '1' to 'A' to rrda,

        // 2 ~ X
        '2' to '0' to da,
        '2' to '1' to la,
        '2' to '2' to a,
        '2' to '3' to ra,
        '2' to '4' to lua, // lua > ula: 沒變；改回 lua
        '2' to '5' to ua,
        '2' to '6' to rua, // rua > "^>A": 沒變；改回 rua
        '2' to '7' to luua, // luua > "^^<A": 沒變；改回 luua
        '2' to '8' to uua,
        '2' to '9' to ruua, // ruua > "^^>A": 128248124990963 變大
        '2' to 'A' to dra, // dra > rda: 沒變；改回 dra

        // 3 ~ X
        '3' to '0' to dla, // lda > dla: 沒變；改回 lda(好像先按 v 比較省)
        '3' to '1' to lla,
        '3' to '2' to la,
        '3' to '3' to a,
        '3' to '4' to llua, // llua > "^<<A": 變大；改回 llua
        '3' to '5' to lua, // lua > "^<A": 沒變；改回 lua
        '3' to '6' to ua,
        '3' to '7' to "<<^^A", // "<<^^A" > "^^<<A": 沒變；改回 "<<^^A"
        '3' to '8' to "^^<A", // luua > "^^<A": 沒變；改回 luua
        '3' to '9' to uua,
        '3' to 'A' to da,

        // 4 ~ X
        '4' to '0' to rdda,
        '4' to '1' to da,
        '4' to '2' to dra, // dra > rda: 沒變；改回 dra
        '4' to '3' to drra, // drra > ">>vA": 沒變；改回 drra
        '4' to '4' to a,
        '4' to '5' to ra,
        '4' to '6' to rra,
        '4' to '7' to ua,
        '4' to '8' to "^>A", // rua
        '4' to '9' to "^>>A", // rrua
        '4' to 'A' to ">>vvA",

        // 5 ~ X
        '5' to '0' to dda,
        '5' to '1' to "v<A", // lda
        '5' to '2' to da,
        '5' to '3' to dra, // dra
        '5' to '4' to la,
        '5' to '5' to a,
        '5' to '6' to ra,
        '5' to '7' to "^<A", // lua
        '5' to '8' to ua,
        '5' to '9' to "^>A", // rua
        '5' to 'A' to "vv>A", // ddra

        // 6 ~ X
        '6' to '0' to "vv<A",
        '6' to '1' to "v<<A",
        '6' to '2' to "v<A",
        '6' to '3' to da,
        '6' to '4' to lla,
        '6' to '5' to la,
        '6' to '6' to a,
        '6' to '7' to llua,
        '6' to '8' to lua,
        '6' to '9' to ua,
        '6' to 'A' to dda,

        // 7 ~ X
        '7' to '0' to rddda,
        '7' to '1' to dda,
        '7' to '2' to ddra,
        '7' to '3' to "vv>>A",
        '7' to '4' to da,
        '7' to '5' to dra,
        '7' to '6' to drra,
        '7' to '7' to a,
        '7' to '8' to ra,
        '7' to '9' to rra,
        '7' to 'A' to ">>vvvA", // ">>vvvA"

        // 8 ~ X
        '8' to '0' to ddda,
        '8' to '1' to "vv<A",
        '8' to '2' to dda,
        '8' to '3' to ddra,
        '8' to '4' to lda,
        '8' to '5' to da,
        '8' to '6' to dra,
        '8' to '7' to la,
        '8' to '8' to a,
        '8' to '9' to ra,
        '8' to 'A' to "vvv>A",

        // 9 ~ X
        '9' to '0' to "<vvvA",
        '9' to '1' to "<<vvA",
        '9' to '2' to ldda,
        '9' to '3' to dda,
        '9' to '4' to llda,
        '9' to '5' to lda,
        '9' to '6' to da,
        '9' to '7' to lla,
        '9' to '8' to la,
        '9' to '9' to a,
        '9' to 'A' to ddda
    )

//        +---+---+
//        | ^ | A |
//    +---+---+---+
//    | < | v | > |
//    +---+---+---+
    val dirMoves = mapOf(
        // A ~ X
        'A' to '^' to la,
        'A' to '<' to "v<<A",
        'A' to 'v' to lda, // dla > lda: 有變小
        'A' to '>' to da,
        'A' to 'A' to a,

        // ^ ~ X
        '^' to '^' to a,
        '^' to '<' to dla,
        '^' to 'v' to da,
        '^' to '>' to rda, // dra > rda： 沒變
        '^' to 'A' to ra,

        // < ~ X
        '<' to '^' to rua,
        '<' to '<' to a,
        '<' to 'v' to ra,
        '<' to '>' to rra,
        '<' to 'A' to rrua,

        // v ~ X
        'v' to '^' to ua,
        'v' to '<' to la,
        'v' to 'v' to a,
        'v' to '>' to ra,
        'v' to 'A' to rua, // rua > ura: 變大 133901671304648 改回 rua

        // > ~ X
        '>' to '^' to lua, // lua > ula: 變大 170186695525171; 改回 lua
        '>' to '<' to lla,
        '>' to 'v' to la,
        '>' to '>' to a,
        '>' to 'A' to ua
    )

    fun part1(input: List<String>): Int {
        val numericCodeList = input.map { line ->
            line.dropLast( 1 ).toInt()
        }

        fun dirKeyMapping( keys: String, repeat: Int ): String {
            if ( repeat == 0 ) {
                return keys
            } else {
                val keyCharList = keys.toMutableList()

                keyCharList.add( 0, 'A' )

                return keyCharList.zipWithNext().joinToString("") { keyPair ->
                    dirKeyMapping( dirMoves.getValue(keyPair), repeat - 1 )
                }
            }
        }

        val lengthList = input.map { line ->
            val chList = line.toMutableList()

            chList.add( 0, 'A' )
            chList.zipWithNext().sumOf { keyPair ->
                val firstPadKeys = numericMoves.getValue( keyPair )
                dirKeyMapping( firstPadKeys, 2 ).length
            }
        }

        var sum = 0

        numericCodeList.indices.forEach { i ->
            sum += numericCodeList[ i ] * lengthList[ i ]
        }

        return sum

    //        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): Int {
        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day21")
    part1(input).println()
    part2(input).println()
}
