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

    private val minSz: Int
        get() = min(width, height) - vertexSize - thickness

    private val rect: Rectangle
        get() = Rectangle((width - minSz)/2, (height-minSz)/2, minSz, minSz)

    private val radius: Int
        get() = minSz / 2

    private val center: Point
        get() = Point(rect.x + radius, rect.y + radius)

    private val phi: Double
        get() = 2 * PI / graph.size

    private var vertexPositions: MutableList<Point>? = null

    private fun calcVertexPositions(){
        vertexPositions = MutableList<Point>(graph.size) { i ->
            Point((center.x + radius * cos(i * phi)).toInt() ,
                    (center.y + radius * sin(i * phi)).toInt()
            )
        }
    }

    fun paint(g: Graphics){
        paintEdges(g)
        paintVerticies(g)
    }

    private fun paintVerticies(g: Graphics) {
        (g as Graphics2D).apply {
            rotate(-PI / 2, center.x.toDouble(), center.y.toDouble())
            vertexPositions?.forEach {
                g.color = Color.WHITE
                g.fillOval(it.x - vertexSize / 2, it.y - vertexSize / 2, vertexSize, vertexSize)
                g.color = Color.BLUE
                g.drawOval(it.x - vertexSize / 2, it.y - vertexSize / 2, vertexSize, vertexSize)
            }
        }
    }

    private fun paintEdges(g: Graphics) {
        (g as Graphics2D).apply {
            rotate(-PI / 2, center.x.toDouble(), center.y.toDouble())
            stroke = BasicStroke(
                    thickness.toFloat(),
                    BasicStroke.CAP_ROUND,
                    BasicStroke.JOIN_ROUND)
            setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            )
        }

        graph.forEachIndexed { fromInd, from ->
            from.takeLast(graph.size - fromInd - 1)
                    .forEachIndexed { toInd, weight ->
                if (weight > 1e-20) {
                    vertexPositions?.let { vPos ->
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