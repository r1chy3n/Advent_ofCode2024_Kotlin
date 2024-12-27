fun main() {
    fun part1(input: List<String>): Long {
        val emptyIndex = input.indexOfFirst { it.isEmpty()}

        // {x00=1, x01=1, x02=1, y00=0, y01=1, y02=0}
        val wireValueMap = input.subList( 0, emptyIndex ).associate { line ->
            line.split( ": " ).let { lineSplit ->
                lineSplit[ 0 ] to lineSplit[ 1 ].toInt()
            }
        }.toMutableMap()

        // {z00=(x00, AND, y00), z01=(x01, XOR, y01), z02=(x02, OR, y02)}
        val gateInfoMap = input.subList( emptyIndex + 1, input.size
        ).associate { line ->
            line.split( " " ).let {
                it[ 4 ] to Triple( it[ 0 ], it[ 1 ], it[ 2 ])
            }
        }

        val zNNList = gateInfoMap.filter {( k, _) ->
            k.startsWith( "z" )
        }.map {( k, _) ->
            k
        }

        fun getOutput( wire: String ): Int {
            if ( wireValueMap.contains( wire )) {
                return wireValueMap.getValue( wire )
            } else {
                val gateInfo = gateInfoMap.getValue( wire )
                val wireValue1 = wireValueMap.get(
                    gateInfo.first ) ?: getOutput( gateInfo.first )
                val wireValue2 = wireValueMap.get(
                    gateInfo.third ) ?: getOutput( gateInfo.third )

                return if ( gateInfo.second == "AND" ) {
                    wireValue1 and wireValue2
                } else if ( gateInfo.second == "OR") {
                    wireValue1 or wireValue2
                } else if ( gateInfo.second == "XOR" ) {
                    wireValue1 xor wireValue2
                } else {
                    0
                }.also { value ->
                    wireValueMap.put( wire, value )
                }
            }
        }

        val zOutputList = MutableList( zNNList.size ) { 0 }

        zNNList.forEach { zNN ->
            zOutputList[ zNN.substring( 1 ).toInt()] = getOutput( zNN )
        }

        return zOutputList.reversed().joinToString( "" ).toLong( 2 )

//        return input.size
    } // fun part1( List<String>)

    fun part2(input: List<String>): String {
        val emptyIndex = input.indexOfFirst { it.isEmpty()}
        val wireValueMap = input.subList( 0, emptyIndex ).associate { line ->
            line.split( ": " ).let { lineSplit ->
                lineSplit[ 0 ] to lineSplit[ 1 ].toInt()
            }
        }.toMutableMap()

        data class Gate(
            val a: String, val b: String, val op: String, var c: String )

        val gateList = input.subList( emptyIndex + 1, input.size
        ).map { line ->
            line.split( " " ).let {
                Gate( it[ 0 ], it[ 2 ], it[ 1 ], it[ 4 ])
            }
        }

        val nxz = gateList.filter {(_, _, operator, output ) ->
            output.first() == 'z' && output != "z45" && operator != "XOR"
        }

        val xnz = gateList.filter {( input1, input2, operator, output ) ->
            input1.first() !in "xy" && input2.first() !in "xy"
                    && output.first() != 'z' && operator == "XOR"
        }

        fun List<Gate>.firstZThatUsesC(c: String): String? {
            val x = filter { it.a == c || it.b == c }
            x.find { it.c.startsWith('z') }?.let { return "z" + (it.c.drop(1).toInt() - 1).toString().padStart(2, '0') }
            return x.firstNotNullOfOrNull { firstZThatUsesC(it.c) }
        }

        for (i in xnz) {
            val b = nxz.first { it.c == gateList.firstZThatUsesC(i.c) }
            val temp = i.c
            i.c = b.c
            b.c = temp
        }

        fun getWiresAsLong( registers: MutableMap<String, Int>, type: Char
        ) = registers.filter { it.key.startsWith( type )
        }.toList().sortedBy { it.first
        }.map { it.second
        }.joinToString( "" ).reversed().toLong(2)

        fun run(gates: List<Gate>, registers: MutableMap<String, Int>): Long {
            val exclude = HashSet<Gate>()

            while (exclude.size != gates.size) {
                val available = gates.filter { a ->
                    a !in exclude && gates.none { b ->
                        (a.a == b.c || a.b == b.c) && b !in exclude
                    }
                }
                for ((a, b, op, c) in available) {
                    val v1 = registers.getOrDefault(a, 0)
                    val v2 = registers.getOrDefault(b, 0)
                    registers[c] = when (op) {
                        "AND" -> v1 and v2
                        "OR" -> v1 or v2
                        "XOR" -> v1 xor v2
                        else -> 0
                    }
                }
                exclude.addAll(available)
            }

            return getWiresAsLong(registers, 'z')
        }

        val falseCarry = ( getWiresAsLong( wireValueMap, 'x' ) +
                getWiresAsLong( wireValueMap, 'y' ) xor run(
            gateList, wireValueMap )).countTrailingZeroBits().toString()

        return ( nxz + xnz + gateList.filter {
            it.a.endsWith(falseCarry) && it.b.endsWith(falseCarry)
        }).map { it.c }.sorted().joinToString(",")

//        return input.size
    } // fun part2( List<String>)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day24")
    part1(input).println()
    part2(input).println()
}
