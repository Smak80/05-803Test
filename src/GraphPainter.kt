import java.awt.*
import kotlin.math.*

class GraphPainter(
        val graph: MutableList<MutableList<Double>>
) {

    private var width = 1
    private var height = 1
    var thickness = 1
        set(value) {
            if (value >=1 && value <= 30) {
                field = value
                calcVertexPositions()
            }
        }
    var vertexSize = 30
        set(value){
            if (value >=10 && value <= 100) {
                field = value
                calcVertexPositions()
            }
        }

    var size: Pair<Int, Int>
    get() = Pair(width, height)
    set(value){
        width = value.first
        height = value.second
        calcVertexPositions()
    }

    private var vertexPositions: MutableList<Point>? = null

    private fun calcVertexPositions(){
        val minSz = min(width, height) - vertexSize - thickness
        val rect = Rectangle((width - minSz)/2, (height-minSz)/2, minSz, minSz)
        val radius = rect.width / 2
        val center = Point(rect.x + radius, rect.y + radius)
        val phi = 2 * PI / graph.size
        vertexPositions = MutableList<Point>(graph.size) { i ->
            Point((center.x + radius * cos(i * phi - PI / 2)).toInt() ,
                    (center.y + radius * sin(i * phi - PI / 2)).toInt()
            )
        }
    }

    fun paint(g: Graphics){
        paintEdges(g)
        paintVerticies(g)
    }

    private fun paintVerticies(g: Graphics) {
        vertexPositions?.forEach {
            g.color = Color.WHITE
            g.fillOval(it.x - vertexSize/2, it.y - vertexSize/2, vertexSize, vertexSize)
            g.color = Color.BLUE
            g.drawOval(it.x - vertexSize/2, it.y - vertexSize/2, vertexSize, vertexSize)
        }
    }

    private fun paintEdges(g: Graphics) {

        graph.forEachIndexed { fromInd, from ->
            from.takeLast(graph.size - fromInd - 1)
                    .forEachIndexed { toInd, weight ->
                if (weight > 1e-20) {
                    vertexPositions?.let { vPos ->
                        (g as Graphics2D).apply {
                            stroke = BasicStroke(
                                    thickness.toFloat(),
                                    BasicStroke.CAP_ROUND,
                                    BasicStroke.JOIN_ROUND)
                            setRenderingHint(
                                    RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON
                            )
                        }
                        val toI = toInd + fromInd + 1
                        g.drawLine(
                                vPos[fromInd].x, vPos[fromInd].y,
                                vPos[toI].x, vPos[toI].y
                        )
                    }
                }
            }
        }

        /*for (i in 0 until graph.size - 1){
            for (j in i+1 until graph.size) {
                if (graph[i][j]>1e-20){
                    vertexPositions?.let { vp ->
                        g.drawLine(vp[i].x, vp[i].y, vp[j].x, vp[j].y)
                    }
                }
            }
        }*/
    }
}