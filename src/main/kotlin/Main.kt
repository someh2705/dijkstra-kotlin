fun main() {

    val graph = listOf(
        Edge(Node("A"), Node("B"), 10.0),
        Edge(Node("B"), Node("D"), 40.0),
        Edge(Node("A"), Node("C"), 30.0),
        Edge(Node("C"), Node("B"), 10.0),
        Edge(Node("C"), Node("D"), 10.0),
    )

    val oneWay = Dijkstra(graph)
    val twoWay = Dijkstra(graph, true)

    println(oneWay.findRoute(Node("A"), Node("D")))
    println(twoWay.findRoute(Node("A"), Node("D")))
}
