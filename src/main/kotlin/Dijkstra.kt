class Dijkstra(
    private val graph: List<Edge>,
    private val isTwoWay: Boolean = false
) {
    private val node = findAllNodes(graph)

    private fun findAllNodes(graph: List<Edge>) =
        graph
            .flatMap { listOf(it.begin, it.end) }
            .toSet()

    private fun Edge.other(who: Node) =
        if (begin != who) begin else end

    private fun Edge.sort(who: Node) =
        if (begin == who) this else copy(begin = end, end = begin)

    private val Node.neighbours
        get() = graph
            .filter { it.begin == this || (isTwoWay && it.end == this) }
            .map {
                it.other(this)
            }

    private val List<Edge>.cost
        get() = when {
            isEmpty() -> Double.POSITIVE_INFINITY
            else -> sumOf { it.cost }
        }

    private fun MutableMap<Node, Record>.register(key: Node, block: Record.() -> Unit) {
        getValue(key).apply(block)
    }

    fun findRoute(from: Node, to: Node): Record {
        val q = node.toMutableSet()
        val destinations: MutableMap<Node, Record> =
            node.associateWith { Record() }.toMutableMap()

        fun dest(key: Node) =
            destinations.getValue(key)

        fun register(key: Node, block: Record.() -> Unit) =
            destinations.register(key, block)

        register(from) {
            cost = 0.0
        }

        while (q.isNotEmpty()) {
            val visit =
                q.minBy { dest(it).cost }

            visit
                .neighbours
                .forEach { neighbour ->
                    graph
                        .filter { (it.begin == visit && it.end == neighbour) || (isTwoWay && it.end == visit && it.begin == neighbour) }
                        .forEach { edge ->
                            val record = dest(visit)
                            val cost = record.cost + edge.cost
                            if (cost < dest(neighbour).cost) {
                                register(neighbour) {
                                    this.cost = cost
                                    if (isTwoWay) {
                                        this.edges = record.edges + edge.sort(visit)
                                    } else {
                                        this.edges = record.edges + edge
                                    }
                                }
                            }
                        }
                }

            q.remove(visit)
        }

        return destinations.getValue(to)
    }
}