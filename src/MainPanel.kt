import java.awt.Graphics
import javax.swing.JPanel

class MainPanel(): JPanel() {

    var painter: Painter? = null

    override fun paint(g: Graphics?) {
        super.paint(g)
        g?.let{
            painter?.paint(it)
        }
    }
}