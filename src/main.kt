fun main() {
    val data = Loader.load("graph2.csv")
    val p = GraphPainter(data)

    with (MainWindow()){
        painter = p
        isVisible = true
    }

}