import AoCUtils.test

fun main() {
    data class Destination(val destination: Long, val source: Long, val length: Long) {

        fun inRange(num: Long) = num >= source && num <= source + length

        fun map(num: Long) = if (inRange(num)) {
            destination + num - source
        } else num

    }

    val mapStrings = listOf(
        "seed-to-soil",
        "soil-to-fertilizer",
        "fertilizer-to-water",
        "water-to-light",
        "light-to-temperature",
        "temperature-to-humidity",
        "humidity-to-location"
    )


    fun part1(input: String, debug: Boolean = false): Long {

        val seedsText = input.lines().first()
        val seeds = seedsText.split(": ")[1].split(" ").map { it.trim() }
            .map { it.toLong() }

        val map = mutableMapOf<String, MutableList<Destination>>()
        var inMapper = false;
        var mapString = ""
        input.lines().drop(2).forEach { line ->
            if (line.isNotEmpty() && line.get(0).isLetter()) {
                inMapper = true
                mapString = line.split(" ")[0]
                map[mapString] = mutableListOf()
//                println("Staring $mapString")
            } else if (line.isNotEmpty() && line.get(0).isDigit()) {
                if (inMapper) {
                    var nums = line.split(" ")
                    val destination = Destination(
                        destination = nums[0].toLong(),
                        source = nums[1].toLong(),
                        length = nums[2].toLong()
                    )
                    map[mapString]?.add(destination)
                } else println { "Not in mapper but digits for line: $line" }
            } else {
//                println("Done $mapString got num of maps: ${map[mapString]?.size}")
                inMapper = false
                mapString = ""
            }
        }
        val list = mutableListOf<Long>()
        seeds.forEach { seed ->
            var newDestinationNum = seed
            mapStrings.forEach { s ->
                val destinations = map[s]!!

                val possibleDestination = destinations
                    .map { destination ->
                        if (destination.inRange(newDestinationNum)) destination.map(newDestinationNum) else null
                    }
                    .map { if (it == null) newDestinationNum else it }

                if (possibleDestination.filter { it != newDestinationNum }.any()) {
                    newDestinationNum = possibleDestination.filter { it != newDestinationNum }.first()
                }
            }
            list.add(newDestinationNum)
        }

        return list.sorted().first()
    }

    fun part2(input: String, debug: Boolean = false): Long {

        val seedsText = input.lines().first()
        val seedsToMap = seedsText.split(": ")[1].split(" ").map { it.trim() }
            .map { it.toLong() }

        val seedPair = mutableListOf<Pair<Long, Long>>()
        for (x in 0 until seedsToMap.size step 2) {
            val pair = Pair(seedsToMap[x], seedsToMap[x + 1])
            seedPair.add(pair)
        }


        val map = mutableMapOf<String, MutableList<Destination>>()
        var inMapper = false;
        var mapString = ""
        input.lines().drop(2).forEach { line ->
            if (line.isNotEmpty() && line.get(0).isLetter()) {
                inMapper = true
                mapString = line.split(" ")[0]
                map[mapString] = mutableListOf()
//                println("Staring $mapString")
            } else if (line.isNotEmpty() && line.get(0).isDigit()) {
                if (inMapper) {
                    var nums = line.split(" ")
                    val destination = Destination(
                        destination = nums[0].toLong(),
                        source = nums[1].toLong(),
                        length = nums[2].toLong()
                    )
                    map[mapString]?.add(destination)
                } else println { "Not in mapper but digits for line: $line" }
            } else {
//                println("Done $mapString got num of maps: ${map[mapString]?.size}")
                inMapper = false
                mapString = ""
            }
        }
        val list = mutableListOf<Long>()

        seedPair.forEach {
            for (seed in it.second..it.first) {
                var newDestinationNum = seed
                mapStrings.forEach { s ->
                    val destinations = map[s]!!

                    val possibleDestination = destinations
                        .map { destination ->
                            if (destination.inRange(newDestinationNum)) destination.map(newDestinationNum) else null
                        }
                        .map { if (it == null) newDestinationNum else it }

                    if (possibleDestination.filter { it != newDestinationNum }.any()) {
                        newDestinationNum = possibleDestination.filter { it != newDestinationNum }.first()
                    }
                }
                list.add(newDestinationNum)
            }
        }


        return list.sorted().first()
    }

    val testInput = "seeds: 79 14 55 13\n" +
            "\n" +
            "seed-to-soil map:\n" +
            "50 98 2\n" +
            "52 50 48\n" +
            "\n" +
            "soil-to-fertilizer map:\n" +
            "0 15 37\n" +
            "37 52 2\n" +
            "39 0 15\n" +
            "\n" +
            "fertilizer-to-water map:\n" +
            "49 53 8\n" +
            "0 11 42\n" +
            "42 0 7\n" +
            "57 7 4\n" +
            "\n" +
            "water-to-light map:\n" +
            "88 18 7\n" +
            "18 25 70\n" +
            "\n" +
            "light-to-temperature map:\n" +
            "45 77 23\n" +
            "81 45 19\n" +
            "68 64 13\n" +
            "\n" +
            "temperature-to-humidity map:\n" +
            "0 69 1\n" +
            "1 0 69\n" +
            "\n" +
            "humidity-to-location map:\n" +
            "60 56 37\n" +
            "56 93 4"

    val input = AoCUtils.readText("year2023/day05.txt")

    part1(testInput) test Pair(35L, "test 1 part 1")
    part1(input) test Pair(322500873L, "part 1")

    part2(testInput, debug = true) test Pair(46L, "test 2 part 2")
    part2(input) test Pair(0L, "part 2")
}
