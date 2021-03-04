import java.awt.Color
import java.awt.Graphics
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.ComponentListener
import javax.swing.GroupLayout
import javax.swing.JFrame
import javax.swing.JPanel

class MainWindow : JFrame(){

    private val mainPanel: JPanel
    var painter: GraphPainter? = null

    init{
        defaultCloseOperation = EXIT_ON_CLOSE

        mainPanel = JPanel().apply {
            background = Color.WHITE
        }

        layout = GroupLayout(contentPane).also {
            it.setHorizontalGroup(
                    it.createSequentialGroup()
                            .addGap(8)
                            .addComponent(mainPanel, 600, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addGap(8)
            )
            it.setVerticalGroup(
                    it.createSequentialGroup()
                            .addGap(8)
                            .addComponent(mainPanel, 500, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE)
                            .addGap(8)
            )
        }
        mainPanel.addComponentListener(object: ComponentAdapter() {
            override fun componentResized(e: ComponentEvent?) {
                painter?.size = Pair(mainPanel.width, mainPanel.height)
            }
        })
        pack()
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        painter?.size = Pair(mainPanel.width, mainPanel.height)
        painter?.paint(mainPanel.graphics)
    }
}