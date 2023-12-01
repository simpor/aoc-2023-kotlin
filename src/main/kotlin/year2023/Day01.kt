import AoCUtils.test

fun main() {

    fun part1(input: String, debug: Boolean = false): Long {
        val elves = input.split("\n")
        val list = elves.map { s ->
            s.toCharArray().filter { c -> c.isDigit() }
                .map { it.toString() }
                .fold("") { a, b -> a + b }
                .toString()
        }.map { num ->
            if (num.toLong() < 10) num + num
            else num.first().toString() + num.last().toString()
        }.map { it.toLong() }
        println { list.toString() }
        return list.sum()
    }

    fun part2(input: String, debug: Boolean = false): Long {
        val numMap = mapOf(
            "one" to "1",
            "two" to "2",
            "three" to "3",
            "four" to "4",
            "five" to "5",
            "six" to "6",
            "seven" to "7",
            "eight" to "8",
            "nine" to "9"
        )

        val list = input.lines()
            .map { s ->
                val numPos = mutableMapOf<Int, Int>()
                var index = 0
                while (index < s.length) {

                    if (s[index].isDigit()) {
                        numPos[index] = s[index].toString().toInt()
                    } else {
                        numMap.forEach { k, v ->
                            if (s.startsWith(k, index)) {
                                numPos[index] = v.toInt()
                            }
                        }
                    }
                    index++
                }
                println { numPos.toString() }

                val first = numPos[numPos.keys.sorted().min()]
                val last = numPos[numPos.keys.sorted().max()]
                first.toString() + last.toString()

            }.map { num ->
                if (num.toLong() < 10) num + num
                else num.first().toString() + num.last().toString()
            }.map { it.toLong() }
        println { list.toString() }
        return list.sum()

    }

    val testInput = "1abc2\n" +
            "pqr3stu8vwx\n" +
            "a1b2c3d4e5f\n" +
            "treb7uchet"

    val input = AoCUtils.readText("year2023/day01.txt")

    part1(testInput) test Pair(142L, "test 1 part 1")
    part1(input) test Pair(53921L, "part 1")

    part2(
        "two1nine\n" +
                "eightwothree\n" +
                "abcone2threexyz\n" +
                "xtwone3four\n" +
                "4nineeightseven2\n" +
                "zoneight234\n" +
                "7pqrstsixteen"
    ) test Pair(281L, "test 2 part 2")

    part2(input) test Pair(54676L, "part 2")

    // not 54678 to high
}
