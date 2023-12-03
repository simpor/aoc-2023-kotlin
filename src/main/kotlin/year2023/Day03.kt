import AoCUtils.test

fun main() {
    data class Engine(val num: Long, val symbol: String)

    fun part1(input: String, debug: Boolean = false): Long {
        val map = parseMap(input) { c -> c }
        val maxX = map.maxOf { it.key.x }
        val maxY = map.maxOf { it.key.y }
        val numMap = mutableMapOf<Pair<Int, Int>, Engine>()

        for (y in 0..maxY) {
            var num = ""
            var symbol = ""
            for (x in 0..maxX) {
                val c = map[Point(x, y)]!!
                if (c.isDigit()) {
                    num += c
                    val around = map.around(Point(x, y), Map2dDirection.entries)
                    val symbolMaybe = around.values.firstOrNull { it != '.' && !it.isDigit() }
                    if (symbolMaybe != null) symbol = symbolMaybe.toString()
                } else {
                    if (num.isNotEmpty()) {
                        numMap[Pair(x, y)] = Engine(num.toLong(), symbol)
                        num = ""
                        symbol = ""
                    }
                }
            }
            if (num.isNotEmpty()) {
                numMap[Pair(maxX, y)] = Engine(num.toLong(), symbol)
            }
        }

        return numMap.values.filter { it.symbol.isNotEmpty() }.map { it.num }.sum()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val map = parseMap(input) { c -> c }
        val maxX = map.maxOf { it.key.x }
        val maxY = map.maxOf { it.key.y }
        val numMap = mutableMapOf<Point, List<Engine>>()

        for (y in 0..maxY) {
            var num = ""
            var symbol = ""
            var symbolPoint = Point(0,0)
            for (x in 0..maxX) {
                val c = map[Point(x, y)]!!
                if (c.isDigit()) {
                    num += c
                    val around = map.around(Point(x, y), Map2dDirection.entries)
                    val symbolMaybe = around.filter { it.value != '.' && !it.value.isDigit() }.entries.firstOrNull()
                    if (symbolMaybe != null) {
                        symbol = symbolMaybe.value.toString()
                        symbolPoint = symbolMaybe.key
                    }
                } else {
                    if (num.isNotEmpty()) {
                        val list = numMap.getOrDefault(symbolPoint, mutableListOf())
                        list.addFirst(Engine(num.toLong(), symbol))
                        numMap[symbolPoint] = list
                        num = ""
                        symbol = ""
                        symbolPoint = Point(0,0)
                    }
                }
            }
            if (num.isNotEmpty()) {
                val list = numMap.getOrDefault(symbolPoint, mutableListOf())
                list.addFirst(Engine(num.toLong(), symbol))
                numMap[symbolPoint] = list
            }
        }

        return numMap.values
            .asSequence()
            .filter { it[0].symbol == "*" }
            .filter { it.size > 1 }
            .map { it.map { it.num }.fold(1L) {acc, l -> acc * l } }
            .sum()

    }

    val testInput = "467..114..\n" +
            "...*......\n" +
            "..35..633.\n" +
            "......#...\n" +
            "617*......\n" +
            ".....+.58.\n" +
            "..592.....\n" +
            "......755.\n" +
            "...\$.*....\n" +
            ".664.598..\n"

    val input = AoCUtils.readText("year2023/day03.txt")

    part1(testInput) test Pair(4361L, "test 1 part 1")
    part1(input) test Pair(543867L, "part 1")

    part2(testInput) test Pair(467835L, "test 2 part 2")
    part2(input) test Pair(79613331L, "part 2")
}
