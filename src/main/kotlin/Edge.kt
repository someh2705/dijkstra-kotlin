data class Edge(
    val begin: Node,
    val end: Node,
    val cost: Double
) {
    override fun toString(): String {
        return "Edge(begin=$begin, end=$end)"
    }
}
